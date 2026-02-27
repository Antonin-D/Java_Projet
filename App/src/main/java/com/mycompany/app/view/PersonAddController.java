package com.mycompany.app.view;

import com.mycompany.app.db.daos.PersonDao;
import com.mycompany.app.db.entities.Person;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import java.time.LocalDate;

/**
 * Contrôleur responsable de la vue d'ajout d'un nouveau contact
 * Fait le lien entre l'interface graphique (PersonnAdd.fxml) et la base de données
 */
public class PersonAddController {

    // Laison avec les champs de texte de l'interface JavaFX
    @FXML private TextField lastnameInput;
    @FXML private TextField firstnameInput;
    @FXML private TextField nicknameInput;
    @FXML private TextField phoneInput;
    @FXML private TextField addressInput;
    @FXML private TextField emailInput;
    @FXML private DatePicker birthDateInput;

    // Instanciation du Data Access Object pour interagir avec la base de données
    private PersonDao personDao = new PersonDao();

    @FXML
    private void handleSavePerson() {
        // Récupération des informations saisies par l'utilisateur
        String lastname = lastnameInput.getText();
        String firstname = firstnameInput.getText();
        String nickname = nicknameInput.getText();
        String phone = phoneInput.getText();
        String address = addressInput.getText();
        String email = emailInput.getText();
        LocalDate birthDate = birthDateInput.getValue();

        // Vérification des champs obligatoires pour éviter les crashs 
        if (lastname.isBlank() || firstname.isBlank()) {
            System.out.println("Erreur: Le nom et le prénom sont obligatoires.");
            return;
        }

        // Création de l'objet Person avec les données du formulaire
        Person newPerson = new Person(lastname, firstname, nickname, phone, address, email, birthDate);

        // Sauvegarde dans la base de données en utilisant la méthode du DAO
        personDao.addPerson(newPerson);

        // Réinitialisation de l'interface et confirmation
        clearForm();
        System.out.println("Personne ajoutée avec succès !");
    }

    /**
     * Méthode utilitaire pour vider tous les champs du formulaire.
     */
    private void clearForm() {
        lastnameInput.clear();
        firstnameInput.clear();
        nicknameInput.clear();
        phoneInput.clear();
        addressInput.clear();
        emailInput.clear();
        birthDateInput.setValue(null);
    }
}