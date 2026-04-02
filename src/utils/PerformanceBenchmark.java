package utils;

import models.WorkoutNode;
import structures.AVLTree;
import structures.UnbalancedBST;
import structures.SinglyLinkedList;
import java.time.LocalDate;
import java.util.List;

/**
 * PerformanceBenchmark - Compares performance of three data structures
 * Assigned to: Jarmaine (Simulation, Benchmarking & UI)
 */
public class PerformanceBenchmark {
    
    /**
     * Benchmark insertion performance
     */
    public static BenchmarkResult benchmarkInsertion(List<WorkoutNode> data, String structureName) {
        long startTime = System.nanoTime();
        
        switch (structureName) {
            case "AVL":
                AVLTree avl = new AVLTree();
                for (WorkoutNode workout : data) {
                    avl.insert(workout);
                }
                break;
            case "BST":
                UnbalancedBST bst = new UnbalancedBST();
                for (WorkoutNode workout : data) {
                    bst.insert(workout);
                }
                break;
            case "LinkedList":
                SinglyLinkedList list = new SinglyLinkedList();
                for (WorkoutNode workout : data) {
                    list.insert(workout);
                }
                break;
        }
        
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        
        return new BenchmarkResult(structureName, "Insertion", data.size(), duration);
    }
    
    /**
     * Benchmark range query performance
     */
    public static BenchmarkResult benchmarkRangeQuery(Object structure, LocalDate startDate, 
                                                       LocalDate endDate, String structureName) {
        long startTime = System.nanoTime();
        List<WorkoutNode> results = null;
        
        if (structure instanceof AVLTree) {
            results = ((AVLTree) structure).rangeQuery(startDate, endDate);
        } else if (structure instanceof UnbalancedBST) {
            results = ((UnbalancedBST) structure).rangeQuery(startDate, endDate);
        } else if (structure instanceof SinglyLinkedList) {
            results = ((SinglyLinkedList) structure).rangeQuery(startDate, endDate);
        }
        
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        
        return new BenchmarkResult(structureName, "Range Query", 
                                   results != null ? results.size() : 0, duration);
    }
    
    /**
     * Benchmark search performance
     */
    public static BenchmarkResult benchmarkSearch(Object structure, LocalDate date, String structureName) {
        long startTime = System.nanoTime();
        
        if (structure instanceof AVLTree) {
            ((AVLTree) structure).search(date);
        } else if (structure instanceof UnbalancedBST) {
            ((UnbalancedBST) structure).search(date);
        } else if (structure instanceof SinglyLinkedList) {
            ((SinglyLinkedList) structure).search(date);
        }
        
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        
        return new BenchmarkResult(structureName, "Search", 1, duration);
    }
    
    /**
     * Print benchmark results in formatted table
     */
    public static void printResults(List<BenchmarkResult> results) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("PERFORMANCE BENCHMARK RESULTS");
        System.out.println("=".repeat(80));
        System.out.printf("%-15s %-15s %-15s %-20s%n", "Structure", "Operation", "Data Size", "Time (ns)");
        System.out.println("-".repeat(80));
        
        for (BenchmarkResult result : results) {
            System.out.printf("%-15s %-15s %-15d %-20d%n", 
                result.structureName, result.operation, result.dataSize, result.durationNanos);
        }
        
        System.out.println("=".repeat(80) + "\n");
    }
    
    /**
     * Inner class to store benchmark results
     */
    public static class BenchmarkResult {
        String structureName;
        String operation;
        int dataSize;
        long durationNanos;
        
        public BenchmarkResult(String structureName, String operation, int dataSize, long durationNanos) {
            this.structureName = structureName;
            this.operation = operation;
            this.dataSize = dataSize;
            this.durationNanos = durationNanos;
        }
    }
}
