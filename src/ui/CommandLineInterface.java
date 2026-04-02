package ui;

import models.WorkoutNode;
import structures.AVLTree;
import structures.UnbalancedBST;
import structures.SinglyLinkedList;
import utils.MockDataGenerator;
import utils.PerformanceBenchmark;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * CommandLineInterface - Interactive user interface
 * Assigned to: Jarmaine (Simulation, Benchmarking & UI)
 */
public class CommandLineInterface {
    
    private AVLTree avlTree;
    private UnbalancedBST bst;
    private SinglyLinkedList linkedList;
    private Scanner scanner;
    private DateTimeFormatter dateFormatter;
    
    public CommandLineInterface() {
        this.avlTree = new AVLTree();
        this.bst = new UnbalancedBST();
        this.linkedList = new SinglyLinkedList();
        this.scanner = new Scanner(System.in);
        this.dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    public void start() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("FITNESS & CONDITIONING DATA TRACKER");
        System.out.println("AVL Tree vs BST vs Linked List Performance Comparison");
        System.out.println("=".repeat(60) + "\n");
        
        boolean running = true;
        while (running) {
            printMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    addWorkout();
                    break;
                case 2:
                    searchWorkout();
                    break;
                case 3:
                    rangeQuery();
                    break;
                case 4:
                    loadMockData();
                    break;
                case 5:
                    runBenchmark();
                    break;
                case 6:
                    displayStatistics();
                    break;
                case 7:
                    clearAllData();
                    break;
                case 8:
                    running = false;
                    System.out.println("\nThank you for using Fitness Tracker!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        
        scanner.close();
    }
    
    private void printMenu() {
        System.out.println("\n--- MAIN MENU ---");
        System.out.println("1. Add Workout");
        System.out.println("2. Search Workout by Date");
        System.out.println("3. Range Query (Date Range)");
        System.out.println("4. Load Mock Data");
        System.out.println("5. Run Performance Benchmark");
        System.out.println("6. Display Statistics");
        System.out.println("7. Clear All Data");
        System.out.println("8. Exit");
    }
    
    private void addWorkout() {
        System.out.println("\n--- Add Workout ---");
        LocalDate date = getDateInput("Enter date (yyyy-MM-dd): ");
        double liftingVolume = getDoubleInput("Enter lifting volume (kg): ");
        int cardioDuration = getIntInput("Enter cardio duration (minutes): ");
        double bodyWeight = getDoubleInput("Enter body weight (kg): ");
        
        WorkoutNode workout = new WorkoutNode(date, liftingVolume, cardioDuration, bodyWeight);
        
        avlTree.insert(workout);
        bst.insert(workout);
        linkedList.insert(workout);
        
        System.out.println("✓ Workout added successfully to all structures!");
    }
    
    private void searchWorkout() {
        System.out.println("\n--- Search Workout ---");
        LocalDate date = getDateInput("Enter date to search (yyyy-MM-dd): ");
        
        WorkoutNode result = avlTree.search(date);
        
        if (result != null) {
            System.out.println("\nWorkout found:");
            System.out.println(result);
        } else {
            System.out.println("No workout found for " + date);
        }
    }
    
    private void rangeQuery() {
        System.out.println("\n--- Range Query ---");
        LocalDate startDate = getDateInput("Enter start date (yyyy-MM-dd): ");
        LocalDate endDate = getDateInput("Enter end date (yyyy-MM-dd): ");
        
        List<WorkoutNode> results = avlTree.rangeQuery(startDate, endDate);
        
        if (results.isEmpty()) {
            System.out.println("No workouts found in this date range.");
        } else {
            System.out.println("\nFound " + results.size() + " workout(s):");
            
            double totalLifting = 0;
            int totalCardio = 0;
            double avgBodyWeight = 0;
            
            for (WorkoutNode workout : results) {
                System.out.println(workout);
                totalLifting += workout.getLiftingVolume();
                totalCardio += workout.getCardioDuration();
                avgBodyWeight += workout.getBodyWeight();
            }
            
            avgBodyWeight /= results.size();
            
            System.out.println("\n--- Summary ---");
            System.out.printf("Total lifting volume: %.2f kg%n", totalLifting);
            System.out.println("Total cardio time: " + totalCardio + " minutes");
            System.out.printf("Average body weight: %.2f kg%n", avgBodyWeight);
        }
    }
    
    private void loadMockData() {
        System.out.println("\n--- Load Mock Data ---");
        System.out.println("1. Chronological data (sequential dates)");
        System.out.println("2. Progressive data (increasing volume)");
        System.out.println("3. Sparse data (random gaps)");
        
        int choice = getIntInput("Select data type: ");
        int numDays = getIntInput("Enter number of days to generate: ");
        
        MockDataGenerator generator = new MockDataGenerator();
        List<WorkoutNode> mockData = null;
        
        LocalDate startDate = LocalDate.now().minusDays(numDays);
        
        switch (choice) {
            case 1:
                mockData = generator.generateChronologicalData(numDays, startDate);
                break;
            case 2:
                mockData = generator.generateProgressiveData(numDays, startDate);
                break;
            case 3:
                mockData = generator.generateSparseData(numDays, startDate, 0.7);
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }
        
        // Clear existing data
        clearAllData();
        
        // Load into all structures
        for (WorkoutNode workout : mockData) {
            avlTree.insert(workout);
            bst.insert(workout);
            linkedList.insert(workout);
        }
        
        generator.printDataSummary(mockData);
        System.out.println("✓ Mock data loaded into all structures!");
    }
    
    private void runBenchmark() {
        System.out.println("\n--- Performance Benchmark ---");
        
        if (avlTree.getSize() == 0) {
            System.out.println("No data loaded. Please load mock data first.");
            return;
        }
        
        List<PerformanceBenchmark.BenchmarkResult> results = new ArrayList<>();
        
        // Benchmark range query (30-day window)
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(30);
        
        System.out.println("Running benchmarks...");
        
        results.add(PerformanceBenchmark.benchmarkRangeQuery(avlTree, startDate, endDate, "AVL Tree"));
        results.add(PerformanceBenchmark.benchmarkRangeQuery(bst, startDate, endDate, "BST"));
        results.add(PerformanceBenchmark.benchmarkRangeQuery(linkedList, startDate, endDate, "Linked List"));
        
        // Benchmark search
        LocalDate searchDate = LocalDate.now().minusDays(15);
        results.add(PerformanceBenchmark.benchmarkSearch(avlTree, searchDate, "AVL Tree"));
        results.add(PerformanceBenchmark.benchmarkSearch(bst, searchDate, "BST"));
        results.add(PerformanceBenchmark.benchmarkSearch(linkedList, searchDate, "Linked List"));
        
        PerformanceBenchmark.printResults(results);
    }
    
    private void displayStatistics() {
        System.out.println("\n--- Data Structure Statistics ---");
        System.out.println("AVL Tree size: " + avlTree.getSize());
        System.out.println("BST size: " + bst.getSize());
        System.out.println("Linked List size: " + linkedList.getSize());
    }
    
    private void clearAllData() {
        avlTree.clear();
        bst.clear();
        linkedList.clear();
        System.out.println("✓ All data cleared.");
    }
    
    // Helper methods for input validation
    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
    
    private double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
    
    private LocalDate getDateInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return LocalDate.parse(input, dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd (e.g., 2024-03-15)");
            }
        }
    }
}
