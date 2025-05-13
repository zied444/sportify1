package Models;

import java.time.LocalDate;

public class Hydratation {

    private LocalDate date;
    private double amount;  // Quantité d'eau bue (en litres)
    private double performanceBefore;  // Performance avant l'hydratation (ex. distance parcourue, temps de course, etc.)
    private double performanceAfter;  // Performance après l'hydratation (ex. distance parcourue, temps de course, etc.)

    // Constructeur
    public Hydratation(LocalDate date, double amount, double performanceBefore, double performanceAfter) {
        this.date = date;
        this.amount = amount;
        this.performanceBefore = performanceBefore;
        this.performanceAfter = performanceAfter;
    }

    // Getters and Setters
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getPerformanceBefore() {
        return performanceBefore;
    }

    public void setPerformanceBefore(double performanceBefore) {
        this.performanceBefore = performanceBefore;
    }

    public double getPerformanceAfter() {
        return performanceAfter;
    }

    public void setPerformanceAfter(double performanceAfter) {
        this.performanceAfter = performanceAfter;
    }

    // Méthode pour analyser l'impact de l'hydratation
    public double getImpact() {
        return performanceAfter - performanceBefore;
    }
}
