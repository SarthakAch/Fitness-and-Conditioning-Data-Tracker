package test;

import java.time.LocalDate;
import structures.AVLTree;
import models.WorkoutNode;

public class BalanceFactorTest {
    public static void main(String[] args) {
        System.out.println("Running BalanceFactorTest...");
        AVLTree tree = new AVLTree();
        // Adding sequential dates to trigger balancing
        tree.insert(new WorkoutNode(LocalDate.of(2024, 1, 1), 100.0, 30, 80.0));
        tree.insert(new WorkoutNode(LocalDate.of(2024, 1, 2), 110.0, 35, 80.0));
        
        int size = tree.getSize();
        if (size != 2) {
            System.out.println("Test failed: expected size 2, got " + size);
            System.exit(1);
        } else {
            System.out.println("Test passed");
        }
    }
}