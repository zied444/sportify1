package Services;

import Models.Evenement;
import Models.Reservation;
import utils.mydb;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ReservationService implements IServices<Reservation> {
    private final Connection connection;
    private final EvenementService evenementService;

    public ReservationService() {
        this.connection = mydb.getInstance().getConn();
        this.evenementService = new EvenementService();
    }

    // Vérifier si l'utilisateur a déjà réservé un événement
    public boolean hasReservation(int utilisateurId, int evenementId) throws SQLException {
        String query = "SELECT COUNT(*) FROM reservation_evenement WHERE utilisateur_id = ? AND evenement_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, utilisateurId);
            statement.setInt(2, evenementId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    // Récupérer un événement par ID (utilitaire)
    private Evenement getEvenementById(int evenementId) throws SQLException {
        List<Evenement> evenements = evenementService.display();
        return evenements.stream()
                .filter(e -> e.getId() == evenementId)
                .findFirst()
                .orElse(null);
    }

    // Réserver un événement pour un utilisateur
    public void reserverEvenement(int utilisateurId, int evenementId, int nbPlaces) throws SQLException {
        // Vérifier si l'utilisateur a déjà réservé cet événement
        if (hasReservation(utilisateurId, evenementId)) {
            throw new SQLException("Vous avez déjà réservé cet événement.");
        }

        // Vérifier les places disponibles
        Evenement evenement = getEvenementById(evenementId);
        if (evenement == null) {
            throw new SQLException("Événement non trouvé avec ID : " + evenementId);
        }
        if (evenement.getNbr_places() < nbPlaces) {
            throw new SQLException("Pas assez de places disponibles. Places restantes : " + evenement.getNbr_places());
        }

        // Ajouter la réservation
        Reservation reservation = new Reservation(
                0, // id_reservation sera généré
                utilisateurId,
                evenementId,
                "inscrit",
                LocalDate.now(),
                0.0f, // Montant à calculer si nécessaire
                nbPlaces
        );
        create(reservation);

        // Mettre à jour les places disponibles
        evenement.setNbr_places(evenement.getNbr_places() - nbPlaces);
        evenementService.update(evenement);
    }

    // Annuler une réservation
    public void annulerReservation(int utilisateurId, int evenementId) throws SQLException {
        // Trouver la réservation
        String selectQuery = "SELECT * FROM reservation_evenement WHERE utilisateur_id = ? AND evenement_id = ?";
        Reservation reservation = null;
        try (PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            statement.setInt(1, utilisateurId);
            statement.setInt(2, evenementId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    reservation = new Reservation(
                            resultSet.getInt("id_reservation"),
                            resultSet.getObject("utilisateur_id", Integer.class),
                            resultSet.getObject("evenement_id", Integer.class),
                            resultSet.getString("statut"),
                            resultSet.getObject("date_res", LocalDate.class),
                            resultSet.getFloat("montant_tot"),
                            resultSet.getInt("nb_places_res")
                    );
                }
            }
        }

        if (reservation == null) {
            throw new SQLException("Aucune réservation trouvée pour l'utilisateur " + utilisateurId + " et l'événement " + evenementId);
        }

        // Supprimer la réservation
        delete(reservation);

        // Restaurer les places dans l'événement
        Evenement evenement = getEvenementById(evenementId);
        if (evenement != null) {
            evenement.setNbr_places(evenement.getNbr_places() + reservation.getNb_places_res());
            evenementService.update(evenement);
        }
    }

    // Méthodes CRUD génériques

    @Override
    public void create(Reservation reservation) throws SQLException {
        String query = "INSERT INTO reservation_evenement (utilisateur_id, evenement_id, statut, date_res, montant_tot, nb_places_res) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setObject(1, reservation.getUtilisateur_id());
            statement.setObject(2, reservation.getEvenement_id());
            statement.setString(3, reservation.getStatut());
            statement.setObject(4, reservation.getDate_res());
            statement.setFloat(5, reservation.getMontant_tot());
            statement.setInt(6, reservation.getNb_places_res());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    reservation.setId_reservation(generatedKeys.getInt(1));
                }
            }
        }
    }

    @Override
    public void update(Reservation reservation) throws SQLException {
        String query = "UPDATE reservation_evenement SET utilisateur_id=?, evenement_id=?, statut=?, date_res=?, montant_tot=?, nb_places_res=? WHERE id_reservation=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, reservation.getUtilisateur_id());
            statement.setObject(2, reservation.getEvenement_id());
            statement.setString(3, reservation.getStatut());
            statement.setObject(4, reservation.getDate_res());
            statement.setFloat(5, reservation.getMontant_tot());
            statement.setInt(6, reservation.getNb_places_res());
            statement.setInt(7, reservation.getId_reservation());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Aucune réservation trouvée avec ID : " + reservation.getId_reservation());
            }
        }
    }

    @Override
    public void delete(Reservation reservation) throws SQLException {
        String query = "DELETE FROM reservation_evenement WHERE id_reservation=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, reservation.getId_reservation());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Aucune réservation trouvée avec ID : " + reservation.getId_reservation());
            }
        }
    }

    @Override
    public List<Reservation> display() throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM reservation_evenement";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Reservation reservation = new Reservation(
                        resultSet.getInt("id_reservation"),
                        resultSet.getObject("utilisateur_id", Integer.class),
                        resultSet.getObject("evenement_id", Integer.class),
                        resultSet.getString("statut"),
                        resultSet.getObject("date_res", LocalDate.class),
                        resultSet.getFloat("montant_tot"),
                        resultSet.getInt("nb_places_res")
                );
                reservations.add(reservation);
            }
        }
        return reservations;
    }
}