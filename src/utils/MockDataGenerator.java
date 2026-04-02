package utils;

import models.WorkoutNode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * MockDataGenerator - Generates realistic fitness data for testing
 * Assigned to: Jarmaine (Simulation, Benchmarking & UI)
 */
public class MockDataGenerator {
    
    private Random random;
    
    public MockDataGenerator() {
        this.random = new Random(42); // Fixed seed for reproducibility
    }
    
    public MockDataGenerator(long seed) {
        this.random = new Random(seed);
    }
    
    /**
     * Generate chronological workout data
     * @param numDays Number of consecutive days to generate
     * @param startDate Starting date
     * @return List of WorkoutNode objects
     */
    public List<WorkoutNode> generateChronologicalData(int numDays, LocalDate startDate) {
        List<WorkoutNode> workouts = new ArrayList<>();
        
        double baseBodyWeight = 70.0 + random.nextDouble() * 30.0; // 70-100 kg
        
        for (int i = 0; i < numDays; i++) {
            LocalDate currentDate = startDate.plusDays(i);
            
            // Simulate realistic lifting volume (500-3000 kg total)
            double liftingVolume = 500 + random.nextDouble() * 2500;
            
            // Simulate cardio duration (0-90 minutes, some days no cardio)
            int cardioDuration = random.nextDouble() < 0.3 ? 0 : random.nextInt(90);
            
            // Body weight fluctuates slightly (+/- 2 kg)
            double bodyWeight = baseBodyWeight + (random.nextDouble() * 4 - 2);
            
            workouts.add(new WorkoutNode(currentDate, liftingVolume, cardioDuration, bodyWeight));
        }
        
        return workouts;
    }
    
    /**
     * Generate data with progressive overload (increasing volume over time)
     */
    public List<WorkoutNode> generateProgressiveData(int numDays, LocalDate startDate) {
        List<WorkoutNode> workouts = new ArrayList<>();
        
        double baseBodyWeight = 75.0;
        double baseLiftingVolume = 1000.0;
        
        for (int i = 0; i < numDays; i++) {
            LocalDate currentDate = startDate.plusDays(i);
            
            // Progressive increase in lifting volume (1% per week)
            double weekProgress = i / 7.0;
            double liftingVolume = baseLiftingVolume * (1 + 0.01 * weekProgress) + 
                                   random.nextDouble() * 200 - 100; // Add variance
            
            // Cardio duration varies
            int cardioDuration = 20 + random.nextInt(40);
            
            // Body weight changes slightly
            double bodyWeight = baseBodyWeight + (random.nextDouble() * 3 - 1.5);
            
            workouts.add(new WorkoutNode(currentDate, liftingVolume, cardioDuration, bodyWeight));
        }
        
        return workouts;
    }
    
    /**
     * Generate sparse data (not every day has a workout)
     */
    public List<WorkoutNode> generateSparseData(int numDays, LocalDate startDate, double probability) {
        List<WorkoutNode> workouts = new ArrayList<>();
        
        double baseBodyWeight = 80.0;
        
        for (int i = 0; i < numDays; i++) {
            // Only generate workout with given probability
            if (random.nextDouble() < probability) {
                LocalDate currentDate = startDate.plusDays(i);
                
                double liftingVolume = 800 + random.nextDouble() * 2000;
                int cardioDuration = random.nextInt(60);
                double bodyWeight = baseBodyWeight + (random.nextDouble() * 4 - 2);
                
                workouts.add(new WorkoutNode(currentDate, liftingVolume, cardioDuration, bodyWeight));
            }
        }
        
        return workouts;
    }
    
    /**
     * Print summary statistics of generated data
     */
    public void printDataSummary(List<WorkoutNode> workouts) {
        if (workouts.isEmpty()) {
            System.out.println("No workout data.");
            return;
        }
        
        double totalLifting = 0;
        int totalCardio = 0;
        double avgBodyWeight = 0;
        
        for (WorkoutNode workout : workouts) {
            totalLifting += workout.getLiftingVolume();
            totalCardio += workout.getCardioDuration();
            avgBodyWeight += workout.getBodyWeight();
        }
        
        avgBodyWeight /= workouts.size();
        
        System.out.println("\n=== Mock Data Summary ===");
        System.out.println("Total workouts: " + workouts.size());
        System.out.println("Date range: " + workouts.get(0).getDate() + " to " + 
                          workouts.get(workouts.size() - 1).getDate());
        System.out.printf("Total lifting volume: %.2f kg%n", totalLifting);
        System.out.println("Total cardio time: " + totalCardio + " minutes");
        System.out.printf("Average body weight: %.2f kg%n", avgBodyWeight);
        System.out.println("========================\n");
    }
}
