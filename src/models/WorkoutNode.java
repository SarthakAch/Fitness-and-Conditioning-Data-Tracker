package models;

import java.time.LocalDate;

/**
 * WorkoutNode - Core data model for storing daily fitness metrics
 * Assigned to: Rami (Foundational Structures & Baselines)
 */
public class WorkoutNode {
    private LocalDate date;
    private double liftingVolume;  // Total weight lifted in kg
    private int cardioDuration;     // Cardio time in minutes
    private double bodyWeight;      // Body weight in kg
    
    public WorkoutNode(LocalDate date, double liftingVolume, int cardioDuration, double bodyWeight) {
        this.date = date;
        this.liftingVolume = liftingVolume;
        this.cardioDuration = cardioDuration;
        this.bodyWeight = bodyWeight;
    }
    
    // Getters
    public LocalDate getDate() {
        return date;
    }
    
    public double getLiftingVolume() {
        return liftingVolume;
    }
    
    public int getCardioDuration() {
        return cardioDuration;
    }
    
    public double getBodyWeight() {
        return bodyWeight;
    }
    
    // Setters
    public void setLiftingVolume(double liftingVolume) {
        this.liftingVolume = liftingVolume;
    }
    
    public void setCardioDuration(int cardioDuration) {
        this.cardioDuration = cardioDuration;
    }
    
    public void setBodyWeight(double bodyWeight) {
        this.bodyWeight = bodyWeight;
    }
    
    @Override
    public String toString() {
        return String.format("Date: %s | Lifting: %.2f kg | Cardio: %d min | Weight: %.2f kg",
                date, liftingVolume, cardioDuration, bodyWeight);
    }
}
