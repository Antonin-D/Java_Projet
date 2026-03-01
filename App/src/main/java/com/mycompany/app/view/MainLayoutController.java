package com.mycompany.app.view;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import com.mycompany.app.App;
import java.io.IOException;

/**
 * Contrôleur principal de l'application (le menu de navigation).
 * Il gère le conteneur principal et permet de changer la vue affichée au centre.
 */
public class MainLayoutController {

    @FXML
    private BorderPane mainBorderPane;

    /**
     * Méthode appelée lors du clic sur le bouton "Liste des contacts".
     * Charge le fichier PersonList.fxml et l'affiche au centre de l'écran.
     * * @throws IOException Si le fichier FXML n'est pas trouvé
     */
    @FXML
    private void showListPersons() throws IOException {
        mainBorderPane.setCenter(App.loadFXML("PersonList"));
    }

    /**
     * Méthode appelée lors du clic sur le bouton "Ajouter un contact".
     * Charge le fichier PersonAdd.fxml et l'affiche au centre de l'écran.
     * * @throws IOException Si le fichier FXML n'est pas trouvé
     */
    @FXML
    private void showAddPerson() throws IOException {
        mainBorderPane.setCenter(App.loadFXML("PersonAdd"));
    }

    /**
     * Méthode appelée lors du clic sur le bouton "Gérer les contacts".
     * Charge le fichier PersonAdmin.fxml et l'affiche au centre de l'écran.
     * * @throws IOException Si le fichier FXML n'est pas trouvé
     */
    @FXML
    private void showAdminPersons() throws IOException {
        mainBorderPane.setCenter(App.loadFXML("PersonAdmin"));
    }
}