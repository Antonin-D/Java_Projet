package com.mycompany.app.view;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane; 
import com.mycompany.app.App;
import java.io.IOException;

public class MainLayoutController {
    @FXML
    private BorderPane mainBorderPane;

    @FXML 
    private void showListPersons() throws IOException{
        mainBorderPane.setCenter(App.loadFXML("PersonList"));
    }

    @FXML
    private void showAddPerson() throws IOException {
        mainBorderPane.setCenter(App.loadFXML("PersonAdd"));
    }
}
