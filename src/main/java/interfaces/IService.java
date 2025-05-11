package interfaces;

import java.util.List;

public interface IService<T> {
    void ajouterUtilisateur(T t);
    T connecter(String email, String motDePasse);
    List<T> listerUtilisateurs();
    void modifierUtilisateur(T t);
    void supprimerUtilisateur(int id);
}
