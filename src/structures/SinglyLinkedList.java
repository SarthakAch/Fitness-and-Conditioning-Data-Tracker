package structures;

import models.WorkoutNode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * SinglyLinkedList - Baseline comparison structure (O(n) operations)
 * Assigned to: Rami (Foundational Structures & Baselines)
 */
public class SinglyLinkedList {
    
    private class Node {
        WorkoutNode data;
        Node next;
        
        Node(WorkoutNode data) {
            this.data = data;
            this.next = null;
        }
    }
    
    private Node head;
    private int size;
    
    public SinglyLinkedList() {
        this.head = null;
        this.size = 0;
    }
    
    /**
     * Insert workout log - O(n) to maintain chronological order
     */
    public void insert(WorkoutNode workout) {
        Node newNode = new Node(workout);
        
        // Empty list
        if (head == null) {
            head = newNode;
            size++;
            return;
        }
        
        // Insert at beginning
        if (workout.getDate().isBefore(head.data.getDate())) {
            newNode.next = head;
            head = newNode;
            size++;
            return;
        }
        
        // Find insertion point
        Node current = head;
        while (current.next != null && current.next.data.getDate().isBefore(workout.getDate())) {
            current = current.next;
        }
        
        newNode.next = current.next;
        current.next = newNode;
        size++;
    }
    
    /**
     * Search for workout by date - O(n)
     */
    public WorkoutNode search(LocalDate date) {
        Node current = head;
        while (current != null) {
            if (current.data.getDate().equals(date)) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }
    
    /**
     * Range query - O(n) to scan through list
     */
    public List<WorkoutNode> rangeQuery(LocalDate startDate, LocalDate endDate) {
        List<WorkoutNode> results = new ArrayList<>();
        Node current = head;
        
        while (current != null) {
            LocalDate currentDate = current.data.getDate();
            if ((currentDate.equals(startDate) || currentDate.isAfter(startDate)) &&
                (currentDate.equals(endDate) || currentDate.isBefore(endDate))) {
                results.add(current.data);
            }
            current = current.next;
        }
        
        return results;
    }
    
    public int getSize() {
        return size;
    }
    
    public void clear() {
        head = null;
        size = 0;
    }
}
