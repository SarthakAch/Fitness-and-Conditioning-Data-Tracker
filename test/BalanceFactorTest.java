import static org.junit.Assert.*;
import org.junit.Test;
import java.time.LocalDate;

public class BalanceFactorTest {
    @Test
    public void testBalanceFactorCalculations() {
        AVLTree tree = new AVLTree();
        // Insert nodes to create a slight imbalance
        tree.insert(new WorkoutNode(10, "Pushups", LocalDate.now()));
        tree.insert(new WorkoutNode(20, "Squats", LocalDate.now()));
        
        // Verify the balance factor is within the legal AVL range [-1, 0, 1]
        int bf = tree.getBalanceFactor(tree.getRoot());
        assertTrue("Balance factor " + bf + " is out of bounds!", Math.abs(bf) <= 1);
    }
}
