package org.example.fineasy.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.fineasy.utils.ShowDialog;
import org.example.fineasy.models.DataManagement;
import org.example.fineasy.models.Transaction;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;


/**
 * The controller for the add transaction page
 * include type selection, fill in amount, comment, date pick
 */
public class AddController implements Initializable {
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


    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Initialize toggle group for inputting data
     * Initialize date picker with current date
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        datePicker.setValue(LocalDate.now()); // Set the DatePicker to the current date
        choiceType.setValue("Expense"); // This will make "Date" the default selected option

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
            int id = generateTransactionId();
            String type = choiceType.getValue();
            double amount = Double.parseDouble(textAmount.getText().trim()); // Ensure valid number format
            Transaction transaction = getTransaction(id, type, amount);

            DataManagement.getInstance().addTransaction(transaction);

            // Assuming save is successful, close the stage
            if (stage != null) {
                stage.close();
            } else {
                closeStage(event); // Fallback to close stage in case 'stage' is not set
            }
        } catch (NumberFormatException e) {
            ShowDialog.showAlert("Error", "Invalid amount. Please enter a valid number.", Alert.AlertType.ERROR);
        } catch (Exception e) {
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
    private Transaction getTransaction(int id, String type, double amount) {
        LocalDate date = datePicker.getValue();

        // Determine the selected category from ToggleButtons
        ToggleButton selectedCategory = (ToggleButton) categoryToggleGroup.getSelectedToggle();
        String category = selectedCategory != null ? selectedCategory.getText() : "None"; // "None" or similar default

        String comment = textComment.getText().trim(); // trim() here as well

        return new Transaction(id, type, amount, date, category, comment);
    }

    @FXML
    private void navigateToMainView(ActionEvent event) {
        stage.close();
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
    private int generateTransactionId() {
        // This needs to be synchronized with actual data management strategies
        int maxId = transactionsObservable.stream()
                .mapToInt(Transaction::getId)
                .max()
                .orElse(0);

        return maxId + 1;
    }


    private final ObservableList<Transaction> transactionsObservable = DataManagement.getInstance().getTransactionsObservable();

}
