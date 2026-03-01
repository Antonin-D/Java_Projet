package com.mycompany.app.view;

import com.mycompany.app.db.daos.PersonDao; 
import com.mycompany.app.db.entities.Person;
import javafx.collections.FXCollections; 
import javafx.collections.ObservableList;
import javafx.fxml.FXML; 
import javafx.scene.control.TableColumn; 
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.transformation.SortedList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TextField;
/**
 * Contrôleur responsable de la vue listant les contacts.
 * Fait le lien entre la base de données et le tableau JavaFX.
 */
public class PersonAdminController {
    // Déclaration du tableau principal
    @FXML
    private TableView<Person> personTable;

    // Déclaration des colonnes du tableau 
    @FXML
    private TableColumn<Person, String> lastnameColumn;
    @FXML
    private TableColumn<Person, String> firstnameColumn;
    @FXML
    private TableColumn<Person, String> phoneColumn;
    @FXML
    private TableColumn<Person, String> nicknameColumn;
    @FXML
    private TableColumn<Person, String> emailColumn;
    @FXML
    private TableColumn<Person, String> addressColumn;
    @FXML
    private TableColumn<Person, java.time.LocalDate> birthDateColumn;
    
    @FXML
    private TextField searchField;
    // Liste source complète (non filtrée)
    private ObservableList<Person> allPersons;
    // Accès aux opérations de la base de données
    private PersonDao personDao = new PersonDao();

    /**
     * Méthode appelée automatiquement par JavaFX lors du chargement de la vue.
     * Elle sert à initialiser et configurer les éléments graphiques.
     */
    @FXML
    public void initialize() {
        // Configuration des colonnes : on indique à chaque colonne quel "getter" de la classe Person elle doit appeler.
        lastnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        firstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        nicknameColumn.setCellValueFactory(new PropertyValueFactory<>("nickname"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("emailAddress"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        birthDateColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));

        // Chargement initial des données depuis la base de données vers le tableau
        refreshTable();
    }

    /**
     * Récupère la liste des contacts depuis la base de données et met à jour l'affichage du tableau.
     */
    private void refreshTable() {
        allPersons = FXCollections.observableArrayList(personDao.listPersons());

        // Liste filtrée qui réagit à la saisie dans le champ de recherche
        FilteredList<Person> filteredPersons = new FilteredList<>(allPersons, p -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredPersons.setPredicate(person -> {
                // Si le champ est vide, on affiche tout
                if (newValue == null || newValue.isBlank()) {
                    return true;
                }

                String filtre = newValue.toLowerCase();

                // Recherche sur nom, prénom, surnom, email, téléphone, adresse
                if (person.getLastname() != null && person.getLastname().toLowerCase().contains(filtre)) return true;
                if (person.getFirstname() != null && person.getFirstname().toLowerCase().contains(filtre)) return true;
                if (person.getNickname() != null && person.getNickname().toLowerCase().contains(filtre)) return true;
                if (person.getEmailAddress() != null && person.getEmailAddress().toLowerCase().contains(filtre)) return true;
                if (person.getPhoneNumber() != null && person.getPhoneNumber().toLowerCase().contains(filtre)) return true;
                if (person.getAddress() != null && person.getAddress().toLowerCase().contains(filtre)) return true;

                return false;
            });
        });

        // SortedList pour conserver le tri des colonnes cliquables
        SortedList<Person> sortedPersons = new SortedList<>(filteredPersons);
        sortedPersons.comparatorProperty().bind(personTable.comparatorProperty());

        personTable.setItems(sortedPersons);
    }
}
