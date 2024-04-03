package org.example.fineasy.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import static org.example.fineasy.utils.AppConstants.PREF_HEIGHT;
import static org.example.fineasy.utils.AppConstants.PREF_WIDTH;

public class LoadNewScene {

    /**
     * @param FXMLpath The FXML file path for the target scene
     * It will load a new scene according to which button be clicked (add, visualization)
     * throws exception when scene load failed
     */public static void loadScene(String FXMLpath, Button sourceButton) {
        try {
            FXMLLoader loader = new FXMLLoader(LoadNewScene.class.getResource(FXMLpath));
            Parent root = loader.load(); // Changed to Parent for generality
            Stage stage = getStage(sourceButton, root);

            stage.show();
        } catch (IOException e) {
            org.example.fineasy.utils.ShowDialog.showAlert("Error", "Failed to load the scene: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private static Stage getStage(Button sourceButton, Parent root) {
        Scene scene = new Scene(root, PREF_WIDTH, PREF_HEIGHT); // Set the preferred size here

        Stage stage = (Stage) sourceButton.getScene().getWindow();

        // Get current stage's position
        double x = stage.getX();
        double y = stage.getY();

        // Set the new scene
        stage.setScene(scene);

        // Set the stage size explicitly if you want to enforce a specific size
         stage.setWidth(PREF_WIDTH);
         stage.setHeight(PREF_HEIGHT);

        // Set the stage's new position to match the old stage's position
        stage.setX(x);
        stage.setY(y);
        return stage;
    }

}
