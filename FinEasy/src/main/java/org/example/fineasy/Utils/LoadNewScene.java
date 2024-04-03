package org.example.fineasy.Utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class LoadNewScene {

    /**
     * @param FXMLpath The FXML file path for the target scene
     * It will load a new scene according to which button be clicked (add, visualization)
     * throws exception when scene load failed
     */
    public static void loadScene(String FXMLpath, Button sourceButton) {
        try {
            FXMLLoader loader = new FXMLLoader(LoadNewScene.class.getResource(FXMLpath));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) sourceButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            ShowDialog.showAlert("Error", "Failed to load the scene: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
