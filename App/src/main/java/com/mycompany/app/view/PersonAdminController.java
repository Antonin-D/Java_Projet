package com.mycompany.app.view;

import com.mycompany.app.db.entities.Person;
import com.mycompany.app.service.PersonService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Contrôleur responsable de la vue listant les contacts avec les actions Modifier et Supprimer.
 */
public class PersonAdminController {

    // Tableau principal
    @FXML private TableView<Person> personTable;

    // Colonnes du tableau
    @FXML private TableColumn<Person, String>    lastnameColumn;
    @FXML private TableColumn<Person, String>    firstnameColumn;
    @FXML private TableColumn<Person, String>    nicknameColumn;
    @FXML private TableColumn<Person, String>    phoneColumn;
    @FXML private TableColumn<Person, String>    emailColumn;
    @FXML private TableColumn<Person, String>    addressColumn;
    @FXML private TableColumn<Person, LocalDate> birthDateColumn;
    @FXML private TableColumn<Person, Void>      actionsColumn;

    // Champs du formulaire de modification (panneau du bas)
    @FXML private TextField    editLastname;
    @FXML private TextField    editFirstname;
    @FXML private TextField    editNickname;
    @FXML private TextField    editPhone;
    @FXML private TextField    editAddress;
    @FXML private TextField    editEmail;
    @FXML private DatePicker   editBirthDate;

    private final PersonService personService = new PersonService();

    /** Personne en cours de modification */
    private Person selectedPerson;

    @FXML
    public void initialize() {
        // Liaison colonnes 
        lastnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        firstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        nicknameColumn.setCellValueFactory(new PropertyValueFactory<>("nickname"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("emailAddress"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        birthDateColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));

        // Ajout des boutons Modifier / Supprimer dans la dernière colonne
        addActionsColumn();

        refreshTable();
    }

    /**
     * Configure la colonne "Actions" avec deux boutons par ligne.
     */
    private void addActionsColumn() {
        Callback<TableColumn<Person, Void>, TableCell<Person, Void>> cellFactory =
            param -> new TableCell<>() {
                private final Button btnEdit   = new Button("✏ Modifier");
                private final Button btnDelete = new Button("🗑 Supprimer");
                private final HBox   box       = new HBox(8, btnEdit, btnDelete);

                {
                    btnEdit.setStyle("-fx-background-color: #4A90D9; -fx-text-fill: white; -fx-cursor: hand;");
                    btnDelete.setStyle("-fx-background-color: #E05252; -fx-text-fill: white; -fx-cursor: hand;");

                    btnEdit.setOnAction(event -> {
                        Person person = getTableView().getItems().get(getIndex());
                        loadPersonIntoForm(person);
                    });

                    btnDelete.setOnAction(event -> {
                        Person person = getTableView().getItems().get(getIndex());
                        handleDeletePerson(person);
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    setGraphic(empty ? null : box);
                }
            };

        actionsColumn.setCellFactory(cellFactory);
    }

    /**
     * Charge les données d'une personne dans le formulaire de modification.
     */
    private void loadPersonIntoForm(Person person) {
        selectedPerson = person;
        editLastname.setText(person.getLastname());
        editFirstname.setText(person.getFirstname());
        editNickname.setText(person.getNickname());
        editPhone.setText(person.getPhoneNumber() != null ? person.getPhoneNumber() : "");
        editAddress.setText(person.getAddress() != null ? person.getAddress() : "");
        editEmail.setText(person.getEmailAddress() != null ? person.getEmailAddress() : "");
        editBirthDate.setValue(person.getBirthDate());
    }

    /**
     * Sauvegarde les modifications du formulaire dans la base de données.
     * Appelé par le bouton "Enregistrer" du formulaire.
     */
    @FXML
    private void handleSaveEdit() {
        if (selectedPerson == null) {
            showAlert(Alert.AlertType.WARNING, "Aucune sélection", "Veuillez d'abord sélectionner une personne à modifier.");
            return;
        }

        String lastname  = editLastname.getText().trim();
        String firstname = editFirstname.getText().trim();
        String nickname  = editNickname.getText().trim();

        if (lastname.isBlank() || firstname.isBlank() || nickname.isBlank()) {
            showAlert(Alert.AlertType.ERROR, "Champs obligatoires", "Le nom, le prénom et le surnom sont obligatoires.");
            return;
        }

        // Mise à jour de l'objet
        selectedPerson.setLastname(lastname);
        selectedPerson.setFirstname(firstname);
        selectedPerson.setNickname(nickname);
        selectedPerson.setPhoneNumber(editPhone.getText().trim());
        selectedPerson.setAddress(editAddress.getText().trim());
        selectedPerson.setEmailAddress(editEmail.getText().trim());
        selectedPerson.setBirthDate(editBirthDate.getValue());

        personService.updatePerson(selectedPerson);

        clearEditForm();
        refreshTable();
        showAlert(Alert.AlertType.INFORMATION, "Succès", "La personne a bien été modifiée.");
    }

    /**
     * Annule la modification en cours et vide le formulaire.
     */
    @FXML
    private void handleCancelEdit() {
        clearEditForm();
    }

    /**
     * Demande confirmation puis supprime la personne sélectionnée.
     */
    private void handleDeletePerson(Person person) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmation de suppression");
        confirm.setHeaderText("Supprimer " + person.getFirstname() + " " + person.getLastname() + " ?");
        confirm.setContentText("Cette action est irréversible.");

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            personService.deletePerson(person.getId());
            // Si on était en train de modifier cette personne, on vide le formulaire
            if (selectedPerson != null && selectedPerson.getId().equals(person.getId())) {
                clearEditForm();
            }
            refreshTable();
        }
    }

    /**
     * Recharge la liste depuis la base de données.
     */
    private void refreshTable() {
        ObservableList<Person> persons = FXCollections.observableArrayList(personService.listPersons());
        personTable.setItems(persons);
    }

    /**
     * Vide le formulaire de modification et réinitialise la sélection.
     */
    private void clearEditForm() {
        selectedPerson = null;
        editLastname.clear();
        editFirstname.clear();
        editNickname.clear();
        editPhone.clear();
        editAddress.clear();
        editEmail.clear();
        editBirthDate.setValue(null);
    }

    /**
     * Affiche une boîte de dialogue simple.
     */
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}