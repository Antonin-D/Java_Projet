package com.mycompany.app.view;

import com.mycompany.app.db.daos.PersonDao;
import com.mycompany.app.db.entities.Person;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import java.time.LocalDate;

public class PersonAddController {

    @FXML private TextField lastnameInput;
    @FXML private TextField firstnameInput;
    @FXML private TextField nicknameInput;
    @FXML private TextField phoneInput;
    @FXML private TextField addressInput;
    @FXML private TextField emailInput;
    @FXML private DatePicker birthDateInput;

    private PersonDao personDao = new PersonDao();

    @FXML
    private void handleSavePerson() {
        // 1. Récupérer les informations tapées par l'utilisateur
        String lastname = lastnameInput.getText();
        String firstname = firstnameInput.getText();
        String nickname = nicknameInput.getText();
        String phone = phoneInput.getText();
        String address = addressInput.getText();
        String email = emailInput.getText();
        LocalDate birthDate = birthDateInput.getValue();

        // Petite vérification pour éviter les crashs (comme vu en cours)
        if (lastname.isBlank() || firstname.isBlank()) {
            System.out.println("Erreur: Le nom et le prénom sont obligatoires.");
            return;
        }

        // 2. Créer l'objet Person
        Person newPerson = new Person(lastname, firstname, nickname, phone, address, email, birthDate);

        // 3. Sauvegarder dans la base de données via le DAO
        personDao.addPerson(newPerson);

        // 4. Vider les champs après l'ajout pour que le formulaire soit propre
        clearForm();
        System.out.println("Personne ajoutée avec succès !");
    }

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