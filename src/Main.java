import ui.CommandLineInterface;

/**
 * Main - Entry point for Fitness & Conditioning Data Tracker
 * 
 * This project demonstrates the performance difference between:
 * - AVL Tree (self-balancing, O(log n) operations)
 * - Unbalanced BST (degrades to O(n) with sequential inserts)
 * - Singly Linked List (O(n) operations)
 * 
 * Team Members:
 * - Rami: WorkoutNode, UnbalancedBST, SinglyLinkedList
 * - Cesar: AVL Tree insertion, rotations, balancing
 * - Sarthak: AVL Tree deletion, range queries
 * - Jarmaine: MockDataGenerator, PerformanceBenchmark, CommandLineInterface
 */
public class Main {
    public static void main(String[] args) {
        CommandLineInterface cli = new CommandLineInterface();
        cli.start();
    }
}
