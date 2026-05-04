import ui.MainWindow;
import javax.swing.SwingUtilities;

/**
 * Main - Entry point for Fitness & Conditioning Data Tracker
 *
 * Team Members:
 * - Rami:    WorkoutNode, SinglyLinkedList
 * - Sarthak: UnbalancedBST
 * - Jarmaine: AVLTree
 * - Cesar:   UI, MockDataGenerator, PerformanceBenchmark
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainWindow window = new MainWindow();
            window.setVisible(true);
        });
    }
}
