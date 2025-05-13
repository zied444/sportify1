package Models;

public class ActiviteSportiveAvancee {

    private String sport;
    private int duree;
    private double poids;
    private double caloriesBrulees;

    // Constructeur
    public ActiviteSportiveAvancee(String sport, int duree, double poids, double caloriesBrulees) {
        this.sport = sport;
        this.duree = duree;
        this.poids = poids;
        this.caloriesBrulees = caloriesBrulees;
    }

    // Getters et setters
    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public double getPoids() {
        return poids;
    }

    public void setPoids(double poids) {
        this.poids = poids;
    }

    public double getCaloriesBrulees() {
        return caloriesBrulees;
    }

    public void setCaloriesBrulees(double caloriesBrulees) {
        this.caloriesBrulees = caloriesBrulees;
    }
}

