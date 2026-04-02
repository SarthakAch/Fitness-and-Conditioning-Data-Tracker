package structures;

import models.WorkoutNode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * AVLTree - Self-balancing binary search tree (maintains O(log n) operations)
 * Assigned to: Cesar (AVL Core & Balancing) - Insertion, rotations, height calculations
 *              Sarthak (Advanced Tree Operations) - Deletion, range queries
 */
public class AVLTree {
    
    private class AVLNode {
        WorkoutNode data;
        AVLNode left;
        AVLNode right;
        int height;
        
        AVLNode(WorkoutNode data) {
            this.data = data;
            this.left = null;
            this.right = null;
            this.height = 1;
        }
    }
    
    private AVLNode root;
    private int size;
    
    public AVLTree() {
        this.root = null;
        this.size = 0;
    }
    
    // ========== HEIGHT AND BALANCE FACTOR (Cesar) ==========
    
    /**
     * Get height of node
     */
    private int height(AVLNode node) {
        return (node == null) ? 0 : node.height;
    }
    
    /**
     * Update height of node based on children
     */
    private void updateHeight(AVLNode node) {
        if (node != null) {
            node.height = 1 + Math.max(height(node.left), height(node.right));
        }
    }
    
    /**
     * Calculate balance factor (left height - right height)
     * Should be -1, 0, or 1 for balanced tree
     */
    private int getBalanceFactor(AVLNode node) {
        return (node == null) ? 0 : height(node.left) - height(node.right);
    }
    
    // ========== ROTATIONS (Cesar) ==========
    
    /**
     * Right rotation
     *       y                x
     *      / \              / \
     *     x   C    -->     A   y
     *    / \                  / \
     *   A   B                B   C
     */
    private AVLNode rotateRight(AVLNode y) {
        AVLNode x = y.left;
        AVLNode B = x.right;
        
        // Perform rotation
        x.right = y;
        y.left = B;
        
        // Update heights
        updateHeight(y);
        updateHeight(x);
        
        return x;
    }
    
    /**
     * Left rotation
     *     x                  y
     *    / \                / \
     *   A   y      -->     x   C
     *      / \            / \
     *     B   C          A   B
     */
    private AVLNode rotateLeft(AVLNode x) {
        AVLNode y = x.right;
        AVLNode B = y.left;
        
        // Perform rotation
        y.left = x;
        x.right = B;
        
        // Update heights
        updateHeight(x);
        updateHeight(y);
        
        return y;
    }
    
    /**
     * Left-Right rotation (double rotation)
     * First rotate left on left child, then rotate right on node
     */
    private AVLNode rotateLeftRight(AVLNode node) {
        node.left = rotateLeft(node.left);
        return rotateRight(node);
    }
    
    /**
     * Right-Left rotation (double rotation)
     * First rotate right on right child, then rotate left on node
     */
    private AVLNode rotateRightLeft(AVLNode node) {
        node.right = rotateRight(node.right);
        return rotateLeft(node);
    }
    
    /**
     * Balance the node based on balance factor
     */
    private AVLNode balance(AVLNode node) {
        if (node == null) {
            return null;
        }
        
        updateHeight(node);
        int balanceFactor = getBalanceFactor(node);
        
        // Left-heavy (balance factor > 1)
        if (balanceFactor > 1) {
            // Left-Left case
            if (getBalanceFactor(node.left) >= 0) {
                return rotateRight(node);
            }
            // Left-Right case
            else {
                return rotateLeftRight(node);
            }
        }
        
        // Right-heavy (balance factor < -1)
        if (balanceFactor < -1) {
            // Right-Right case
            if (getBalanceFactor(node.right) <= 0) {
                return rotateLeft(node);
            }
            // Right-Left case
            else {
                return rotateRightLeft(node);
            }
        }
        
        return node;
    }
    
    // ========== INSERTION (Cesar) ==========
    
    /**
     * Insert workout log - O(log n) guaranteed
     */
    public void insert(WorkoutNode workout) {
        root = insertRecursive(root, workout);
        size++;
    }
    
    private AVLNode insertRecursive(AVLNode node, WorkoutNode workout) {
        // Standard BST insertion
        if (node == null) {
            return new AVLNode(workout);
        }
        
        if (workout.getDate().isBefore(node.data.getDate())) {
            node.left = insertRecursive(node.left, workout);
        } else if (workout.getDate().isAfter(node.data.getDate())) {
            node.right = insertRecursive(node.right, workout);
        } else {
            // Date already exists - update the workout data
            node.data = workout;
            size--; // Don't increment size for updates
            return node;
        }
        
        // Balance the tree after insertion
        return balance(node);
    }
    
    // ========== DELETION (Sarthak) ==========
    
    /**
     * Delete workout by date - O(log n) with cascading rebalancing
     */
    public boolean delete(LocalDate date) {
        int initialSize = size;
        root = deleteRecursive(root, date);
        return size < initialSize;
    }
    
    private AVLNode deleteRecursive(AVLNode node, LocalDate date) {
        if (node == null) {
            return null;
        }
        
        // Standard BST deletion
        if (date.isBefore(node.data.getDate())) {
            node.left = deleteRecursive(node.left, date);
        } else if (date.isAfter(node.data.getDate())) {
            node.right = deleteRecursive(node.right, date);
        } else {
            // Node found - delete it
            size--;
            
            // Case 1: Node with only one child or no child
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }
            
            // Case 2: Node with two children
            // Get inorder successor (smallest in right subtree)
            AVLNode successor = findMin(node.right);
            node.data = successor.data;
            node.right = deleteRecursive(node.right, successor.data.getDate());
        }
        
        // Balance the tree after deletion
        return balance(node);
    }
    
    private AVLNode findMin(AVLNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
    
    // ========== SEARCH ==========
    
    /**
     * Search for workout by date - O(log n)
     */
    public WorkoutNode search(LocalDate date) {
        return searchRecursive(root, date);
    }
    
    private WorkoutNode searchRecursive(AVLNode node, LocalDate date) {
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
    
    // ========== RANGE QUERY (Sarthak) ==========
    
    /**
     * Range query - Custom in-order traversal to extract data between dates
     * O(log n + k) where k is the number of results
     */
    public List<WorkoutNode> rangeQuery(LocalDate startDate, LocalDate endDate) {
        List<WorkoutNode> results = new ArrayList<>();
        rangeQueryRecursive(root, startDate, endDate, results);
        return results;
    }
    
    private void rangeQueryRecursive(AVLNode node, LocalDate startDate, LocalDate endDate, List<WorkoutNode> results) {
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
