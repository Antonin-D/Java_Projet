package com.mycompany.app.view;

import com.mycompany.app.db.daos.PersonDao; 
import com.mycompany.app.model.Person; 
import javafx.collections.FXCollections; 
import javafx.collections.ObservableList;
import javafx.fxml.FXML; 
import javafx.scene.control.TableColumn; 
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class PersonAdminController {
    @FXML
    private TableView<Person> personTable;
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

    private PersonDao personDao = new PersonDao();

    @FXML
    public void initialize() {
        // 1. Configurer les colonnes pour qu'elles aillent lire les getters de Person
        // Attention: "lastname" ici doit correspondre exactement à "getLastname()" dans Person.java
        lastnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        firstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        nicknameColumn.setCellValueFactory(new PropertyValueFactory<>("nickname"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("emailAddress"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        birthDateColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));

        // 2. Charger les données depuis la base de données
        refreshTable();
    }

    private void refreshTable() {
        // Récupère la liste depuis ta BDD via le DAO
        ObservableList<Person> persons = FXCollections.observableArrayList(personDao.listPersons());
        personTable.setItems(persons);
    }
}
