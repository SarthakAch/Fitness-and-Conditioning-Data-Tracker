package test;

import java.time.LocalDate;
import structures.AVLTree;
import models.WorkoutNode;

public class RebalanceTest {
    public static void main(String[] args) {
        System.out.println("Running RebalanceTest...");
        AVLTree tree = new AVLTree();
        // Right-Left Case: Forces a double rotation
        tree.insert(new WorkoutNode(LocalDate.now().minusDays(1), 30.0, 10, 80.0));
        tree.insert(new WorkoutNode(LocalDate.now(), 10.0, 20, 80.0));
        tree.insert(new WorkoutNode(LocalDate.now().plusDays(1), 20.0, 30, 80.0));

        int size = tree.getSize();
        if (size != 3) {
            System.out.println("Test failed: expected size 3, got " + size);
            System.exit(1);
        } else {
            System.out.println("Test passed");
        }
    }
}