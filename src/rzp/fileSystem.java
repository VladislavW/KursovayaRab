package rzp;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import static rzp.MatrixOperations.*;
/**
 * Интерфейс для работы с файловой системой
 */
public interface fileSystem {
    /**
     * Класс представляет графический интерфейс для работы с матрицами
     */
    class MatrixOperationsGUI extends JFrame {
        private JTextField matrix1FileTextField;
        private JTextField matrix2FileTextField;
        public static JTextArea resultTextArea;
        /**
         * Конструктор класса MatrixOperationsGUI
         * Создает и настраивает окно для работы с матрицами
         */
        public MatrixOperationsGUI() {
            setTitle("Работа с двумя матрицами");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(1000, 450);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());

            JPanel matrixFilesPanel = new JPanel();
            matrixFilesPanel.setLayout(new FlowLayout());

            JLabel matrix1Label = new JLabel("Путь к файлу первой матрицы:");
            matrix1FileTextField = new JTextField(20);
            JButton matrix1BrowseButton = new JButton("Browse");
            matrix1BrowseButton.addActionListener(new FileBrowseListener(matrix1FileTextField));

            JLabel matrix2Label = new JLabel("Путь к файлу второй матрицы:");
            matrix2FileTextField = new JTextField(20);
            JButton matrix2BrowseButton = new JButton("Browse");
            matrix2BrowseButton.addActionListener(new FileBrowseListener(matrix2FileTextField));

            matrixFilesPanel.add(matrix1Label);
            matrixFilesPanel.add(matrix1FileTextField);
            matrixFilesPanel.add(matrix1BrowseButton);
            matrixFilesPanel.add(matrix2Label);
            matrixFilesPanel.add(matrix2FileTextField);
            matrixFilesPanel.add(matrix2BrowseButton);

            JPanel operationButtonsPanel = new JPanel();
            operationButtonsPanel.setLayout(new FlowLayout());

            JButton addButton = new JButton("Сложение");
            addButton.addActionListener(new OperationButtonListener("Сложение"));

            JButton subtractButton = new JButton("Вычитание");
            subtractButton.addActionListener(new OperationButtonListener("Вычитание"));

            JButton multiplyButton = new JButton("Умножение");
            multiplyButton.addActionListener(new OperationButtonListener("Умножение"));

            JButton determinantButton = new JButton("Поиск определителя двух матриц");
            determinantButton.addActionListener(new OperationButtonListener("Поиск определителя двух матриц"));

            operationButtonsPanel.add(addButton);
            operationButtonsPanel.add(subtractButton);
            operationButtonsPanel.add(multiplyButton);
            operationButtonsPanel.add(determinantButton);

            addButton.setVisible(true);
            subtractButton.setVisible(true);
            multiplyButton.setVisible(true);
            determinantButton.setVisible(true);

            resultTextArea = new JTextArea(20, 40);
            resultTextArea.setEditable(false);
            resultTextArea.setFont(new Font("Arial", Font.PLAIN, 14));

            add(matrixFilesPanel, BorderLayout.NORTH);
            add(operationButtonsPanel, BorderLayout.CENTER);
            add(new JScrollPane(resultTextArea), BorderLayout.SOUTH);
        }
        /**
         * Класс для кнопок выбора файлов
         */
        private class FileBrowseListener implements ActionListener {
            private JTextField textField;
            /**
             * Конструктор класса FileBrowseListener
             *
             * @param textField текстовое поле, для которого создается слушатель
             */
            public FileBrowseListener(JTextField textField) {
                this.textField = textField;
            }
            /**
             * Вызывается при нажатии на кнопку "Browse"
             * Открывает диалоговое окно для выбора файла и устанавливает путь к выбранному файлу в текстовое поле
             *
             * @param e событие, вызвавшее метод
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    textField.setText(selectedFile.getAbsolutePath());
                }
            }
        }
        /**
         * Класс слушателя для кнопок операций
         */
        class OperationButtonListener implements ActionListener {
            public String operation;
            /**
             * Конструктор класса OperationButtonListener
             *
             * @param operation название операции, соответствующей кнопке
             */
            public OperationButtonListener(String operation) {
                this.operation = operation;
            }
            /**
             * Вызывается при нажатии на кнопку операции
             * Считывает файлы с матрицами, выполняет выбранную операцию и выводит результат в текстовую область
             *
             * @param e событие, вызвавшее метод
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String matrix1File = matrix1FileTextField.getText();
                String matrix2File = matrix2FileTextField.getText();

                int[][] matrix1 = readMatrixFromFile(matrix1File);
                int[][] matrix2 = readMatrixFromFile(matrix2File);

                if (matrix1 == null || matrix2 == null) {
                    resultTextArea.setFont(new Font(Font.MONOSPACED, Font.BOLD, 24));
                    resultTextArea.setText("Выбраны неправильные файлы.\nПроверьте входные данные.");
                    return;
                }

                int[][] resultMatrix = null;
                switch (operation) {
                    case "Сложение":
                        resultMatrix = addMatrices(matrix1, matrix2);
                        if (resultMatrix == null) {
                            resultTextArea.setFont(new Font(Font.MONOSPACED, Font.BOLD, 24));
                            resultTextArea.setText("Выбраны неверные входные данные.\nПроверьте правильность текстового файла.\nПример файла:\n 3 3 - Размер матрицы.\n 3 0 4\n 9 2 3 - Значения матрицы.\n 4 3 1\n Либо данная операция невозможна с конкретными матрицами.");
                        } else {
                            displayMatrix(resultMatrix, "сложения матриц");
                        }

                        break;
                    case "Вычитание":
                        resultMatrix = subtractMatrices(matrix1, matrix2);
                        if (resultMatrix == null) {
                            resultTextArea.setFont(new Font(Font.MONOSPACED, Font.BOLD, 24));
                            resultTextArea.setText("Выбраны неверные входные данные.\nПроверьте правильность текстового файла.\nПример файла:\n 3 3 - Размер матрицы.\n 3 0 4\n 9 2 3 - Значения матрицы.\n 4 3 1\n Либо данная операция невозможна с конкретными матрицами.");
                        } else {
                            displayMatrix(resultMatrix, "вычитания первой матрицы из второй");
                        }

                        break;
                    case "Умножение":
                        resultMatrix = multiplyMatrices(matrix1, matrix2);
                        if (resultMatrix == null) {
                            resultTextArea.setFont(new Font(Font.MONOSPACED, Font.BOLD, 24));
                            resultTextArea.setText("Выбраны неверные входные данные.\nПроверьте правильность текстового файла.\nПример файла:\n 3 3 - Размер матрицы.\n 3 0 4\n 9 2 3 - Значения матрицы.\n 4 3 1\n Либо данная операция невозможна с конкретными матрицами.");
                        } else {
                            displayMatrix(resultMatrix, "умножения первой матрицы на вторую");
                        }
                        break;
                    case "Поиск определителя двух матриц":
                        if (matrix1.length != matrix1[0].length && matrix2.length != matrix2[0].length) {
                            resultTextArea.setFont(new Font(Font.MONOSPACED, Font.BOLD, 24));
                            resultTextArea.setText("Обе матрицы не являются квадратными");
                            return;
                        }
                        if (matrix1.length == matrix1[0].length && matrix2.length != matrix2[0].length) {
                            int determinant1 = calculateDeterminant(matrix1);
                            resultTextArea.setFont(new Font(Font.MONOSPACED, Font.BOLD, 24));
                            resultTextArea.setText("Определитель матрицы 1: " + determinant1 + "\nВторая матрица не является квадратной.\n(Определитель вычислить невозможно)");
                            return;
                        }
                        if (matrix1.length != matrix1[0].length && matrix2.length == matrix2[0].length) {
                            int determinant2 = calculateDeterminant(matrix2);
                            resultTextArea.setFont(new Font(Font.MONOSPACED, Font.BOLD, 24));
                            resultTextArea.setText("Определитель матрицы 2: " + determinant2 + "\nПервая матрица не является квадратной.\n(Определитель вычислить невозможно)");
                            return;
                        }
                        if (matrix1.length == matrix1[0].length && matrix2.length == matrix2[0].length) {
                            int determinant1 = calculateDeterminant(matrix1);
                            int determinant2 = calculateDeterminant(matrix2);
                            resultTextArea.setFont(new Font(Font.MONOSPACED, Font.BOLD, 24));
                            resultTextArea.setText("Определитель матрицы 1: " + determinant1 + "\n");
                            resultTextArea.append("Определитель матрицы 2: " + determinant2);
                        }
                }
            }
        }

    }
}

