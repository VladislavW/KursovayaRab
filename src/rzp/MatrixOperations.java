package rzp;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import static rzp.fileSystem.MatrixOperationsGUI.resultTextArea;
/**
 Класс MatrixOperations предоставляет набор статических методов для работы с матрицами
 */
public class MatrixOperations {
    /**
     * Считывает матрицу из файла.
     *
     * @param filePath путь к файлу
     * @return двумерный массив, представляющий считанную матрицу
     */
    public static int[][] readMatrixFromFile(String filePath) {
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            int rows = scanner.nextInt();
            int columns = scanner.nextInt();

            int[][] matrix = new int[rows][columns];

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    matrix[i][j] = scanner.nextInt();
                }
            }

            return matrix;
        } catch (FileNotFoundException e) {
            System.out.println();
        }

        return null;
    }

    /**
     * Складывает две матрицы.
     *
     * @param matrix1 первая матрица
     * @param matrix2 вторая матрица
     * @return результирующая матрица после сложения
     */
    public static int[][] addMatrices(int[][] matrix1, int[][] matrix2) {
        if (matrix1.length != matrix2.length || matrix1[0].length != matrix2[0].length) {
            return null;
        }

        int rows = matrix1.length;
        int columns = matrix1[0].length;
        int[][] resultMatrix = new int[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                resultMatrix[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }
        return resultMatrix;
    }

    /**
     * Вычитает две матрицы.
     *
     * @param matrix1 первая матрица
     * @param matrix2 вторая матрица
     * @return результирующая матрица после вычитания
     */
    public static int[][] subtractMatrices(int[][] matrix1, int[][] matrix2) {
        if (matrix1.length != matrix2.length || matrix1[0].length != matrix2[0].length) {
            return null;
        }

        int rows = matrix1.length;
        int columns = matrix1[0].length;
        int[][] resultMatrix = new int[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                resultMatrix[i][j] = matrix1[i][j] - matrix2[i][j];
            }
        }

        return resultMatrix;
    }

    /**
     * Умножает две матрицы.
     *
     * @param matrix1 первая матрица
     * @param matrix2 вторая матрица
     * @return результирующая матрица после умножения
     */
    public static int[][] multiplyMatrices(int[][] matrix1, int[][] matrix2) {
        if (matrix1[0].length != matrix2.length) {
            return null;
        }

        int rows = matrix1.length;
        int columns = matrix2[0].length;
        int[][] resultMatrix = new int[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                for (int k = 0; k < matrix1[0].length; k++) {
                    resultMatrix[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }

        return resultMatrix;
    }

    /**
     * Вычисляет определитель матрицы.
     *
     * @param matrix матрица
     * @return определитель матрицы
     */
    public static int calculateDeterminant(int[][] matrix) {
        int n = matrix.length;

        if (n == 1) {
            return matrix[0][0];
        }

        int determinant = 0;
        int sign = 1;

        for (int i = 0; i < n; i++) {
            int[][] submatrix = new int[n - 1][n - 1];
            int submatrixRow = 0;
            int submatrixColumn = 0;

            for (int j = 1; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    if (k != i) {
                        submatrix[submatrixRow][submatrixColumn] = matrix[j][k];
                        submatrixColumn++;

                        if (submatrixColumn == n - 1) {
                            submatrixRow++;
                            submatrixColumn = 0;
                        }
                    }
                }
            }

            determinant += sign * matrix[0][i] * calculateDeterminant(submatrix);
            sign = -sign;
        }

        return determinant;
    }

    /**
     * Отображает матрицу в текстовом виде.
     *
     * @param matrix матрица
     * @param operation операция, выполняемая над матрицей
     */
    public static void displayMatrix(int[][] matrix, String operation) {
        resultTextArea.setText("Результат " + operation + ":\n");
        StringBuilder sb = new StringBuilder();
        for (int[] row : matrix) {
            for (int value : row) {
                sb.append(String.format("%5d", value));
            }
            sb.append("\n");
        }
        String matrixString = sb.toString();

        resultTextArea.setFont(new Font(Font.MONOSPACED, Font.BOLD, 24));
        resultTextArea.append(matrixString);
    }
}

