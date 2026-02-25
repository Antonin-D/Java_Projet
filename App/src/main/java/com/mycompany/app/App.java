package com.mycompany.app;

import com.mycompany.app.db.daos.DataSourceFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;

public class App extends Application {

    private static Scene scene;

    @Override
    public void init() throws Exception {
        // Initialisation de la base de données au démarrage
        try (Connection connection = DataSourceFactory.getConnection();
             Statement statement = connection.createStatement()) {
             
            // On lit le fichier SQL depuis le dossier resources
            InputStream is = App.class.getResourceAsStream("/sql/database-creation.sql");
            if (is != null) {
                String sql = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                statement.executeUpdate(sql);
                System.out.println("Base de données initialisée avec succès.");
            } else {
                System.out.println("Fichier SQL introuvable !");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("MainLayout"), 800, 600);
        stage.setScene(scene);
        stage.setTitle("Contact App");
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}