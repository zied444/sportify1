package Service;

import Models.ActiviteSportiveGraphique;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class VisualisationGraphiqueService {

    public List<ActiviteSportiveGraphique> getDataForChart() {
        List<ActiviteSportiveGraphique> data = new ArrayList<>();

        // Exemple de données pour un sport, vous pouvez les récupérer depuis une base de données ou autre source
        List<Pair<String, String>> points = new ArrayList<>();
        points.add(new Pair<>("1", "100"));
        points.add(new Pair<>("2", "150"));
        points.add(new Pair<>("3", "200"));

        ActiviteSportiveGraphique activite = new ActiviteSportiveGraphique("Course", points);
        data.add(activite);

        return data;
    }
}
