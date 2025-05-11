package main;
import java.time.format.DateTimeFormatter;
import java.sql.Date;

import Models.Evenement;
import Services.EvenementService;
import utils.mydb;

public class Main {
    public static void main(String[] args) {
        EvenementService sc = new EvenementService();
        mydb.getInstance().getConn();

        Evenement e1 = new Evenement("adrenaline", "tunis", new Date(System.currentTimeMillis()), "course", "une course 200m", 50);
        int userId = 1; // Change cette valeur selon l'ID d'un utilisateur existant
        e1.setUtilisateur_id(userId);
        System.out.println("Tentative de création d'événement avec utilisateur_id = " + userId);

        try {
            sc.create(e1);
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}
