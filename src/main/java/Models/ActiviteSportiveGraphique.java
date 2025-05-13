package Models;

import javafx.util.Pair;
import java.util.List;

public class ActiviteSportiveGraphique {

    private String sport;
    private List<Pair<String, String>> points; // Une liste de paires où chaque paire représente un point (X, Y)

    public ActiviteSportiveGraphique(String sport, List<Pair<String, String>> points) {
        this.sport = sport;
        this.points = points;
    }

    public String getSport() {
        return sport;
    }

    public List<Pair<String, String>> getPoints() {
        return points;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public void setPoints(List<Pair<String, String>> points) {
        this.points = points;
    }
}
