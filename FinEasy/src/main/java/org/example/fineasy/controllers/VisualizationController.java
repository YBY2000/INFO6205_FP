package org.example.fineasy.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.example.fineasy.models.DataManagement;
import org.example.fineasy.models.Transaction;

import java.util.HashMap;
import java.util.Map;

import static org.example.fineasy.utils.LoadNewScene.loadScene;

public class VisualizationController {

    public Button btnBack;

    @FXML
    private PieChart categoryPieChart;
    @FXML
    private Label expenditureLabel;
    @FXML
    private Label incomeLabel;
    @FXML
    private Label totalLabel;

    @FXML
    public void initialize() {
        refreshData();
    }

    public void refreshData() {
        ObservableList<Transaction> transactions = DataManagement.getInstance().getTransactionsObservable();

        // Map to store the total amounts by category
        Map<String, Double> totalByCategory = new HashMap<>();

        double totalExpenditure = 0;
        double totalIncome = 0;

        // Aggregate the amounts by category
        for (Transaction transaction : transactions) {
            String category = transaction.getCategory();
            double amount = transaction.getAmount();
            totalByCategory.put(category, totalByCategory.getOrDefault(category, 0.0) + amount);

            // Update totals for expenditure and income
            if ("Expense".equals(transaction.getType())) {
                totalExpenditure += amount;
            } else if ("Income".equals(transaction.getType())) {
                totalIncome += amount;
            }
        }

        // Convert the totals by category to PieChart.Data
        ObservableList<Data> categoryData = FXCollections.observableArrayList();
        totalByCategory.forEach((category, amount) -> categoryData.add(new Data(category, amount)));

        // Update pie chart and labels
        categoryPieChart.setData(categoryData);
        expenditureLabel.setText(String.format("$ %.2f", totalExpenditure));
        incomeLabel.setText(String.format("$ %.2f", totalIncome));
        totalLabel.setText(String.format("Total: $ %.2f", totalIncome - totalExpenditure));
    }


    @FXML
    private void navigateToMainView(ActionEvent event) {
        loadScene("/org/example/fineasy/mainView.fxml", btnBack);
    }
}
