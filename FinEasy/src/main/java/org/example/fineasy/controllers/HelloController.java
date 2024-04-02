package org.example.fineasy.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.fineasy.controllers.AddController;
import org.example.fineasy.exception.TransactionNotFoundException;
import org.example.fineasy.models.DataManagementSingleton;
import org.example.fineasy.models.OperationRecord;
import org.example.fineasy.models.Transaction;

import java.io.IOException;
import java.time.LocalDate;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private BorderPane borderPane; // 确保这是 BorderPane 类型

    @FXML
    private Button addButton; // This is the new button for "add"

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

    //MainView Form initial
    public void initialize() {
        // Setup column bindings
        setupColumnBindings();

        // Bind TableView to observable list
        updateTransactionsView();
    }

    private void setupColumnBindings() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        commentColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));
    }


    public void updateTransactionsView() {
        transactionTable.setItems(DataManagementSingleton.getInstance().getTransactionsObservable());
    }

    @FXML
    public void handleAddButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/org/example/fineasy/addView.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) addButton.getScene().getWindow();
            stage.setScene(scene);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // 处理加载视图失败的情况
        }
    }

    

    public void handleVisualizationButtonClick() {
        try {
            // 更新这里的路径，确保它是正确的
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/fineasy/visualView.fxml"));
            if (loader.getLocation() == null) {
                System.err.println("无法加载视图，检查FXML文件路径是否正确");
                return;
            }

            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) addButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("加载视图失败: " + e.getMessage());
            // 可以在这里添加更多的错误处理，如显示一个错误对话框等
        }
    }

    @FXML
    public void handleDeleteButtonClick() throws TransactionNotFoundException {
        Transaction selectedTransaction = transactionTable.getSelectionModel().getSelectedItem();
        if (selectedTransaction != null) {
            String transactionId = selectedTransaction.getId();
            // Assuming DataManagementSingleton has a deleteTransaction method that takes an ID as a string
            DataManagementSingleton.getInstance().deleteTransaction(transactionId);
            updateTransactionsView(); // Refresh the table view to reflect the deletion
        } else {
            // Optionally, show an alert or notification that no row is selected
            System.out.println("No transaction selected for deletion.");
        }
    }

    @FXML
    public void handleUndoButtonClick() {
        try {
            DataManagementSingleton.getInstance().undoLastAction();
            updateTransactionsView(); // Refresh the table view to show the current state of transactions
        } catch (TransactionNotFoundException e) {
            // Log the error or notify the user if necessary
            e.printStackTrace();
        }
    }


}

