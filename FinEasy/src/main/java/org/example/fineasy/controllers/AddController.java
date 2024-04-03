package org.example.fineasy.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.fineasy.utils.ShowDialog;
import org.example.fineasy.models.DataManagement;
import org.example.fineasy.models.Transaction;

import java.io.IOException;
import java.time.LocalDate;

import static org.example.fineasy.utils.LoadNewScene.loadScene;

/**
 * The controller for the add transaction page
 * include type selection, fill in amount, comment, date pick
 */
public class AddController {
    public Button btnSave;
    public Button btnCancel;
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
     * Initialize the toggle group for inputting data
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
            Transaction transaction = getTransaction(id, type, amount);
            DataManagement.getInstance().addTransaction(transaction);

            navigateToMainView(event);
        } catch (Exception e) {
            // Broader exception handling
            ShowDialog.showAlert("Error", "Error saving transaction: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * The method to get the data of transaction that user input
     *
     * @param id The id of the transaction that user input
     * @param type The type of the transaction that user input
     * @param amount The amount of the transaction that user input
     * @return The Transaction Object to be stored
     */
    private Transaction getTransaction(String id, String type, double amount) {
        LocalDate date = datePicker.getValue();

        // Determine the selected category from ToggleButtons
        ToggleButton selectedCategory = (ToggleButton) categoryToggleGroup.getSelectedToggle();
        String category = selectedCategory != null ? selectedCategory.getText() : "None"; // "None" or similar default

        String comment = textComment.getText().trim(); // trim() here as well

        return new Transaction(id, type, amount, date, category, comment);
    }

    @FXML
    private void navigateToMainView(ActionEvent event) {
        loadScene("/org/example/fineasy/mainView.fxml", btnSave);
    }


    private void closeStage(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * The automatic ID generator for transaction, based on timestamp
     *
     * @return The generated id
     */
    private String generateTransactionId() {
        return String.valueOf(System.currentTimeMillis());
    }
}
