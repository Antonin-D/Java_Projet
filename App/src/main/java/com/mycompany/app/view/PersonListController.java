package com.mycompany.app.view;

import com.mycompany.app.db.entities.Person;
import com.mycompany.app.service.PersonService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

/**
 * Contrôleur de la vue PersonList.fxml.
 * Affiche simplement la liste de tous les contacts en lecture seule.
 */
public class PersonListController {

    @FXML private TableView<Person>                personTable;
    @FXML private TableColumn<Person, String>      lastnameColumn;
    @FXML private TableColumn<Person, String>      firstnameColumn;
    @FXML private TableColumn<Person, String>      nicknameColumn;
    @FXML private TableColumn<Person, String>      phoneColumn;
    @FXML private TableColumn<Person, String>      emailColumn;
    @FXML private TableColumn<Person, String>      addressColumn;
    @FXML private TableColumn<Person, LocalDate>   birthDateColumn;

    private final PersonService personService = new PersonService();

    @FXML
    public void initialize() {
        lastnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        firstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        nicknameColumn.setCellValueFactory(new PropertyValueFactory<>("nickname"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("emailAddress"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        birthDateColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));

        ObservableList<Person> persons = FXCollections.observableArrayList(personService.listPersons());
        personTable.setItems(persons);
    }
}