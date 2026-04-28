package test;

import java.time.LocalDate;
import java.util.List;
import structures.AVLTree;
import models.WorkoutNode;

public class DateRangeTest {
    public static void main(String[] args) {
        System.out.println("Running DateRangeTest...");
        AVLTree tree = new AVLTree();
        LocalDate start = LocalDate.of(2024, 1, 1);
        LocalDate middle = LocalDate.of(2024, 1, 15);
        LocalDate end = LocalDate.of(2024, 2, 1);

        tree.insert(new WorkoutNode(start, 100.0, 30, 80.0));
        tree.insert(new WorkoutNode(middle, 110.0, 35, 80.0));
        tree.insert(new WorkoutNode(end, 120.0, 40, 80.0));

        // Query only for January
        List<WorkoutNode> results = tree.rangeQuery(start, middle);
        
        if (results.size() != 2) {
            System.out.println("Test failed: expected 2 workouts, got " + results.size());
            System.exit(1);
        }
        // Verify the February workout is NOT in the list
        for (WorkoutNode node : results) {
            if (node.getDate().equals(end)) {
                System.out.println("Test failed: Found a workout outside the range!");
                System.exit(1);
            }
        }
        System.out.println("Test passed");
    }
}