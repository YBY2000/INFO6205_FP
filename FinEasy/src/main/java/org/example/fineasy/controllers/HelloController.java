package org.example.fineasy.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.fineasy.Utils.ShowDialog;
import org.example.fineasy.Utils.TransactionNotFoundException;
import org.example.fineasy.models.DataManagement;
import org.example.fineasy.models.Transaction;

import java.time.LocalDate;

import static org.example.fineasy.Utils.LoadNewScene.loadScene;

/**
 * The controller for the main page
 * include add, undo, delete, visualization button
 */
public class HelloController {
    @FXML
    private Button addButton;
    @FXML
    private Button visualizationButton;
    @FXML
    private TableView<Transaction> transactionTable;
    @FXML
    private TableColumn<Transaction, String> typeColumn;
    @FXML
    private TableColumn<Transaction, Double> amountColumn;
    @FXML
    private TableColumn<Transaction, LocalDate> dateColumn;
    @FXML
    private TableColumn<Transaction, String> categoryColumn;
    @FXML
    private TableColumn<Transaction, String> commentColumn;
    @FXML
    private TableColumn<Transaction, String> idColumn;

    public HelloController() {
    }

    /**
     * Initialize the main view
     */
    public void initialize() {
        // Setup column bindings
        setupColumnBindings();

        // Bind TableView to observable list
        updateTransactionsView();
        DataManagement.getInstance().addSampleTransactions();
    } // end initialize


    /**
     * Bind the transactions list table column
     */
    private void setupColumnBindings() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        amountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        categoryColumn.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());
        commentColumn.setCellValueFactory(cellData -> cellData.getValue().commentProperty());
    } // end setupColumnBindings


    /**
     * Update the transactions list view
     */
    public void updateTransactionsView() {
        transactionTable.setItems(DataManagement.getInstance().getTransactionsObservable());
    } // end updateTransactionsView

    /**
     * Handle when the add button be clicked
     * load the new scene - add transaction
     */
    @FXML
    public void handleAddButtonClick() {
        loadScene("/org/example/fineasy/addView.fxml", addButton);
    } // end handleAddButtonClick


    /**
     * Handle when the visualization button be clicked
     * load a new scene - visualization page
     */
    public void handleVisualizationButtonClick() {
        loadScene("/org/example/fineasy/visualView.fxml", visualizationButton);
    } // end handleVisualizationButtonClick


    /**
     * Handle when delete button be clicked
     * It will delete the row that user selected
     * throws exception when no row selected or transaction not found
     */
    @FXML
    public void handleDeleteButtonClick() {
        Transaction selectedTransaction = transactionTable.getSelectionModel().getSelectedItem();
        if (selectedTransaction != null) {
            boolean confirmed = ShowDialog.showConfirmationDialog("Confirm Delete", "Are you sure you want to delete this transaction?");
            if (confirmed) {
                try {
                    DataManagement.getInstance().deleteTransaction(selectedTransaction.getId());
                } catch (TransactionNotFoundException e){
                    ShowDialog.showAlert("Error", "Transaction not found: " + e.getMessage(), Alert.AlertType.ERROR);
                }
                updateTransactionsView();
            } // end if (confirmed)
        } else {
            ShowDialog.showAlert("Warning", "No transaction selected for deletion.", Alert.AlertType.WARNING);
        } // end if (selectedTransaction != null) - else
    } // end handleDeleteButtonClick


    /**
     * Handle when undo button be clicked
     * It will undo the latest user operation
     * throws exception when no latest operation found or transaction not found
     */
    @FXML
    public void handleUndoButtonClick() {
        boolean confirmed = ShowDialog.showConfirmationDialog("Confirm Undo", "Are you sure you want to undo the latest operation?");
        if (confirmed) {
            try {
                DataManagement.getInstance().undoLastAction();
                updateTransactionsView();
            } catch (TransactionNotFoundException e) {
                ShowDialog.showAlert("Error", "Unable to undo the last action: " + e.getMessage(), Alert.AlertType.ERROR);
            } // end try-catch
            updateTransactionsView();
        } // end if (confirmed)

    } // end handleUndoButtonClick

} // end HelloController

