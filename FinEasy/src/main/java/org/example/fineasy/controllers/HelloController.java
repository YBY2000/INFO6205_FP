package org.example.fineasy.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.fineasy.models.LinkedBag;
import org.example.fineasy.models.SortingServiceImpl;
import org.example.fineasy.service.SortingService;
import org.example.fineasy.utils.ShowDialog;
import org.example.fineasy.utils.TransactionNotFoundException;
import org.example.fineasy.models.DataManagement;
import org.example.fineasy.models.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

import static org.example.fineasy.utils.LoadNewScene.loadScene;

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
    @FXML
    private ComboBox<String> sortComboBox;
    private SortingService<Transaction> sortingService;

    public HelloController() {

    }

    /**
     * Initialize the main view
     */
    public void initialize() {
        sortingService = new SortingServiceImpl();
        // Setup column bindings
        setupColumnBindings();

        setupSortComboBox();

        // Bind TableView to observable list
        updateTransactionsView();

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
    public void handleDeleteButtonClick() throws TransactionNotFoundException {
        Transaction selectedTransaction = transactionTable.getSelectionModel().getSelectedItem();
        if (selectedTransaction != null) {
            boolean confirmed = ShowDialog.showConfirmationDialog("Confirm Delete", "Are you sure you want to delete this transaction?");
            if (confirmed) {
                DataManagement.getInstance().deleteTransaction(selectedTransaction.getId());
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

    /**
     * Set up the ComboBox for sorting with an event handler.
     */
    private void setupSortComboBox() {
        sortComboBox.setItems(FXCollections.observableArrayList("Amount", "Date", "Type", "Category"));
        sortComboBox.getSelectionModel().clearSelection(); // Clear the selection to allow placeholder text

        // Sets the sort on selection event handler
        sortComboBox.setOnAction(event -> {
            String selectedCriterion = sortComboBox.getSelectionModel().getSelectedItem();
            if (selectedCriterion != null && !selectedCriterion.isEmpty()) {
                sortTransactions(selectedCriterion);
            }
        });
    }
    private void sortTransactions(String criterion) {
        // Step 1: Transform ObservableList to LinkedBag
        LinkedBag<Transaction> linkedBag = new LinkedBag<>();
        for (Transaction transaction : transactionTable.getItems()) {
            linkedBag.add(transaction);
        }

        // Step 2: Define Comparator
        Comparator<Transaction> comparator = getTransactionComparator(criterion);

        // Step 3: use sort method in LinkedBag
        if (comparator != null) {
            linkedBag.sort(comparator);
        }

        // Step 4: Transform LinkedBag back to ObservableList
        ObservableList<Transaction> sortedList = FXCollections.observableArrayList();
        for (Transaction transaction : linkedBag) {
            sortedList.add(transaction);
        }

        // Step 5: update TableView
        transactionTable.setItems(sortedList);
        transactionTable.refresh();
    }

    //Returns the corresponding Comparator according to the sorting criteria
    private Comparator<Transaction> getTransactionComparator(String criterion) {
        switch (criterion) {
            case "Amount":
                return Comparator.comparing(Transaction::getAmount);
            case "Date":
                return Comparator.comparing(Transaction::getDate);
            case "Type":
                return Comparator.comparing(Transaction::getType);
            case "Category":
                return Comparator.comparing(Transaction::getCategory);
            default:
                return null; // 如果无法识别标准，返回null
        }
    }
//    private void sortTransactions(String criterion) {
//        ObservableList<Transaction> observableList = transactionTable.getItems();
//        sortingService.sort(observableList, criterion);
//        transactionTable.setItems(observableList); // 重新设置排序后的列表
//        transactionTable.refresh(); // 刷新表格显示
//    }
} // end HelloController

