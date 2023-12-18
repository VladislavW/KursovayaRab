import rzp.fileSystem;
import javax.swing.SwingUtilities;

/**
 * Запускает программу для работы с файловой системой
 */
public class Main {
    /**
     * Главный метод программы.
     * @param args параметры командной строки
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            fileSystem.MatrixOperationsGUI frame = new fileSystem.MatrixOperationsGUI();
            frame.setVisible(true);
        });
    }
}
