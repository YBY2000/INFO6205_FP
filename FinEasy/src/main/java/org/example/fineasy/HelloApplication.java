package org.example.fineasy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static org.example.fineasy.Utils.AppConstants.PREF_HEIGHT;
import static org.example.fineasy.Utils.AppConstants.PREF_WIDTH;

/**
 * The launcher of the project
 */
public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("mainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), PREF_WIDTH, PREF_HEIGHT);
        stage.setTitle("FINEASY");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}