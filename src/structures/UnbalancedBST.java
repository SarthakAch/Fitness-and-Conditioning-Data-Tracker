package structures;

import models.WorkoutNode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * UnbalancedBST - Binary Search Tree without balancing (degrades to O(n) with sequential inserts)
 * Assigned to: Rami (Foundational Structures & Baselines)
 */
public class UnbalancedBST {
    
    private class BSTNode {
        WorkoutNode data;
        BSTNode left;
        BSTNode right;
        
        BSTNode(WorkoutNode data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }
    
    private BSTNode root;
    private int size;
    
    public UnbalancedBST() {
        this.root = null;
        this.size = 0;
    }
    
    /**
     * Insert workout log - O(log n) best case, O(n) worst case with sequential dates
     */
    public void insert(WorkoutNode workout) {
        root = insertRecursive(root, workout);
        size++;
    }
    
    private BSTNode insertRecursive(BSTNode node, WorkoutNode workout) {
        if (node == null) {
            return new BSTNode(workout);
        }
        
        if (workout.getDate().isBefore(node.data.getDate())) {
            node.left = insertRecursive(node.left, workout);
        } else if (workout.getDate().isAfter(node.data.getDate())) {
            node.right = insertRecursive(node.right, workout);
        }
        // If dates are equal, we could update the existing node or ignore
        
        return node;
    }
    
    /**
     * Search for workout by date - O(log n) best case, O(n) worst case
     */
    public WorkoutNode search(LocalDate date) {
        return searchRecursive(root, date);
    }
    
    private WorkoutNode searchRecursive(BSTNode node, LocalDate date) {
        if (node == null) {
            return null;
        }
        
        if (date.equals(node.data.getDate())) {
            return node.data;
        } else if (date.isBefore(node.data.getDate())) {
            return searchRecursive(node.left, date);
        } else {
            return searchRecursive(node.right, date);
        }
    }
    
    /**
     * Range query - In-order traversal collecting nodes in date range
     */
    public List<WorkoutNode> rangeQuery(LocalDate startDate, LocalDate endDate) {
        List<WorkoutNode> results = new ArrayList<>();
        rangeQueryRecursive(root, startDate, endDate, results);
        return results;
    }
    
    private void rangeQueryRecursive(BSTNode node, LocalDate startDate, LocalDate endDate, List<WorkoutNode> results) {
        if (node == null) {
            return;
        }
        
        // If current date is after start, explore left subtree
        if (node.data.getDate().isAfter(startDate)) {
            rangeQueryRecursive(node.left, startDate, endDate, results);
        }
        
        // If current date is in range, add it
        LocalDate currentDate = node.data.getDate();
        if ((currentDate.equals(startDate) || currentDate.isAfter(startDate)) &&
            (currentDate.equals(endDate) || currentDate.isBefore(endDate))) {
            results.add(node.data);
        }
        
        // If current date is before end, explore right subtree
        if (node.data.getDate().isBefore(endDate)) {
            rangeQueryRecursive(node.right, startDate, endDate, results);
        }
    }
    
    public int getSize() {
        return size;
    }
    
    public void clear() {
        root = null;
        size = 0;
    }
}
