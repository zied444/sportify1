package Service;

import Models.Hydratation;

import java.util.ArrayList;
import java.util.List;

public class HydratationService {

    private List<Hydratation> hydratationData;

    public HydratationService() {
        this.hydratationData = new ArrayList<>();
    }

    // Ajouter une nouvelle entrée d'hydratation
    public void addHydratation(Hydratation hydratation) {
        hydratationData.add(hydratation);
    }

    // Récupérer toutes les données d'hydratation
    public List<Hydratation> getAllHydratationData() {
        return hydratationData;
    }

    // Analyser l'impact de l'hydratation sur les performances
    public double calculateAverageImpact() {
        double totalImpact = 0;
        int count = 0;
        for (Hydratation h : hydratationData) {
            totalImpact += h.getImpact();
            count++;
        }
        return count == 0 ? 0 : totalImpact / count;
    }
}

