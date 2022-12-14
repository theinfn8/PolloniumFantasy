package com.chaostek.polloniumfantasy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * JavaFX App
 */
    enum editMode
    {
        CREATE,
        READ,
        UPDATE,
        DELETE
    }

public class App extends Application {

    private static Scene scene;
    public static final String REGEX = "^[a-zA-Z0-9,+\\-\\s\\.\\(\\)\\/\\:\\;\\%\\']+$";

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 800, 600);
        stage.setTitle("Pollonium Character Vault");
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}