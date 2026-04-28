import static org.junit.Assert.*;
import org.junit.Test;
import java.time.LocalDate;

public class RebalanceTest {
    @Test
    public void testCascadingRebalance() {
        AVLTree tree = new AVLTree();
        // Right-Left Case: Forces a double rotation
        tree.insert(new WorkoutNode(30, "Run", LocalDate.now()));
        tree.insert(new WorkoutNode(10, "Swim", LocalDate.now()));
        tree.insert(new WorkoutNode(20, "Bike", LocalDate.now()));

        // If rebalancing worked, 20 should have been promoted to the root
        assertEquals("Tree failed to rebalance: Root should be 20", 20, tree.getRoot().getId());
    }
}
