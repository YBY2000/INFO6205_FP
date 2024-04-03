package org.example.fineasy.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.fineasy.Utils.ShowDialog;
import org.example.fineasy.models.DataManagementSingleton;
import org.example.fineasy.models.Transaction;

import java.io.IOException;
import java.time.LocalDate;

/**
 * The controller for the add transaction page
 * include type selection, fill in amount, comment, date pick
 */
public class AddController {
    @FXML
    private ChoiceBox<String> choiceType;
    @FXML
    private TextField textComment;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField textAmount;

    @FXML private ToggleButton toggleFood;
    @FXML private ToggleButton toggleEducation;
    @FXML private ToggleButton toggleTransportation;
    @FXML private ToggleButton toggleEntertainment;
    @FXML private ToggleButton toggleDaily;
    @FXML private ToggleButton toggleGift;
    private ToggleGroup categoryToggleGroup;

    /**
     * Initialize the toggle group for inputing data
     */
    @FXML
    public void initialize() {
        categoryToggleGroup = new ToggleGroup();
        toggleFood.setToggleGroup(categoryToggleGroup);
        toggleEducation.setToggleGroup(categoryToggleGroup);
        toggleTransportation.setToggleGroup(categoryToggleGroup);
        toggleEntertainment.setToggleGroup(categoryToggleGroup);
        toggleDaily.setToggleGroup(categoryToggleGroup);
        toggleGift.setToggleGroup(categoryToggleGroup);
    }

    /**
     * @param event The save button click event
     * Handle the event when the save button be clicked
     */
    @FXML
    private void handleSaveAction(ActionEvent event) {
        try {
            String id = generateTransactionId();
            String type = choiceType.getValue();
            double amount = Double.parseDouble(textAmount.getText().trim()); // trim() to remove leading/trailing spaces
            LocalDate date = datePicker.getValue();

            // Determine the selected category from ToggleButtons
            ToggleButton selectedCategory = (ToggleButton) categoryToggleGroup.getSelectedToggle();
            String category = selectedCategory != null ? selectedCategory.getText() : "None"; // "None" or similar default

            String comment = textComment.getText().trim(); // trim() here as well

            Transaction transaction = new Transaction(id, type, amount, date, category, comment);
            DataManagementSingleton.getInstance().addTransaction(transaction);

            System.out.println("Transaction saved successfully.");
            navigateToMainView(event);
        } catch (Exception e) {
            // Broader exception handling
            ShowDialog.showAlert("Error", "Error saving transaction: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void navigateToMainView(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/fineasy/mainView.fxml"));
            Parent mainViewRoot = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the scene with the specific width and height to reset its size
            Scene mainViewScene = new Scene(mainViewRoot, 800, 800);
            stage.setScene(mainViewScene);

            HelloController helloController = loader.getController();
            if (helloController != null) {
                helloController.updateTransactionsView();
            }

            stage.show();
        } catch (IOException e) {
            ShowDialog.showAlert("Error", "Error navigating to the main view: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }




    @FXML
    private void handleCancelAction(ActionEvent event) {
        try {
            // Load the MainView FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/fineasy/mainView.fxml"));
            Parent mainViewRoot = loader.load();

            // Get the current window (Stage) and set the new scene (Scene)
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the scene with specific width and height to reset its size
            Scene mainViewScene = new Scene(mainViewRoot, 800, 800);
            stage.setScene(mainViewScene);

            // Optionally, if there is any need to perform actions on the controller of MainView
            HelloController controller = loader.getController();
            if (controller != null) {
                controller.updateTransactionsView(); // This line is optional depending on your needs
            }

            // Show the stage with MainView
            stage.show();
        } catch (IOException e) {
            ShowDialog.showAlert("Error", "Error navigating to the main view: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    private void closeStage(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private String generateTransactionId() {
        // 实现ID生成逻辑，例如基于时间戳或其他
        return String.valueOf(System.currentTimeMillis());
    }
}
