package org.example.fineasy.controllers;

import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.fineasy.models.SortingServiceImpl;
import org.example.fineasy.service.SortingService;
import org.example.fineasy.utils.ShowDialog;
import org.example.fineasy.utils.TransactionNotFoundException;
import org.example.fineasy.models.DataManagement;
import org.example.fineasy.models.Transaction;
import org.example.fineasy.models.BinarySearchTree;

import java.time.LocalDate;
import java.util.Comparator;

import static org.example.fineasy.utils.AppConstants.PREF_HEIGHT;
import static org.example.fineasy.utils.AppConstants.PREF_WIDTH;
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
    @FXML
    private TextField searchTextField;
    private SortingService<Transaction> sortingService;
    private BinarySearchTree<Transaction> binarySearchTree;

    public HelloController() {

    }

    /**
     * Initialize the main view
     */public void initialize() {
        sortingService = new SortingServiceImpl<>();
        binarySearchTree = DataManagement.getInstance().getBinarySearchTree();

        // Setup column bindings
        setupColumnBindings();

        setupSortComboBox();

        // Set "Date" as the default value for the sortComboBox
        sortComboBox.setValue("Date"); // This will make "Date" the default selected option

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
        try {
            // Load the FXML for the Add Transaction popup
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/fineasy/addView.fxml"));
            Parent root = loader.load();

            // Create a new stage for the popup, make it modal
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add Transaction");
            stage.setScene(new Scene(root, PREF_WIDTH-200, PREF_HEIGHT-400));
            stage.setResizable(false);

            // Get the controller for the Add Transaction page
            AddController addController = loader.getController();
            addController.setStage(stage);  // Ensure the controller can close the stage

            // Show the modal and wait for it to close
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            ShowDialog.showAlert("Error", "Failed to load the Add Transaction page.", Alert.AlertType.ERROR);
        }
    }


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
    @SuppressWarnings("ClassEscapesDefinedScope")
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


    /**
     * Sorts transactions based on the specified criterion or performs a search if the criterion is "Search".
     *
     * @param criterion The criterion to sort by or "Search" to perform a search.
     */
    private void sortTransactions(String criterion) {
        String selectedCriterion = sortComboBox.getSelectionModel().getSelectedItem();
        if (selectedCriterion != null && !selectedCriterion.isEmpty()) {
            ObservableList<Transaction> observableList;
            if (selectedCriterion.equals("Search")) {
                String keyword = searchTextField.getText(); // Get the search keyword
                observableList = FXCollections.observableArrayList(binarySearchTree.searchByKeyword(keyword)); // Call the search method
            } else {
                observableList = transactionTable.getItems();
                Comparator<Transaction> comparator = getComparatorForCriterion(criterion);
                sortingService.sort(observableList, comparator); // Use Comparator to sort
            }
            transactionTable.setItems(observableList); // Update the TableView after sorting or searching
            transactionTable.refresh(); // Refresh the view
        }
    }

    private Comparator<Transaction> getComparatorForCriterion(String criterion) {
        return switch (criterion) {
            case "Amount" -> Transaction.getAmountComparator();
            case "Date" -> Transaction.getDateComparator();
            case "Type" -> Transaction.getTypeComparator();
            case "Category" -> Transaction.getCategoryComparator();
            default -> throw new IllegalArgumentException("Unrecognized sorting criteria: " + criterion);
        };
    }


    @FXML
    public void handleSearchButtonAction() {
        String keyword = searchTextField.getText();
        if (!keyword.isEmpty()) {
            List<Transaction> searchResult = binarySearchTree.searchByKeyword(keyword);
            ObservableList<Transaction> observableSearchResult = FXCollections.observableArrayList(searchResult);
            transactionTable.setItems(observableSearchResult);
        } else {
            updateTransactionsView();
        }
    }
}

