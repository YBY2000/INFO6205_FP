package org.example.fineasy.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.example.fineasy.models.DataManagement;
import org.example.fineasy.models.Transaction;

import java.util.HashMap;
import java.util.Map;

import static org.example.fineasy.utils.LoadNewScene.loadScene;

public class VisualizationController {

    public Button btnBack;

    @FXML
    private PieChart expenditurePieChart; // New PieChart for expenditure
    @FXML
    private PieChart incomePieChart;      // New PieChart for income
    @FXML
    private Label expenditureLabel;
    @FXML
    private Label incomeLabel;
    @FXML
    private Label totalLabel;
    @FXML
    private VBox expenditureChartContainer;
    @FXML
    private VBox incomeChartContainer;

    @FXML
    public void initialize() {
        refreshData();
    }


    public void refreshData() {
        ObservableList<Transaction> transactions = DataManagement.getInstance().getTransactionsObservable();

        Map<String, Double> expenditureByCategory = new HashMap<>();
        Map<String, Double> incomeByCategory = new HashMap<>();

        double totalExpenditure = 0.0;
        double totalIncome = 0.0;

        for (Transaction transaction : transactions) {
            String category = transaction.getCategory();
            double amount = transaction.getAmount();

            if ("Expense".equals(transaction.getType())) {
                totalExpenditure += amount;
                expenditureByCategory.put(category, expenditureByCategory.getOrDefault(category, 0.0) + amount);
            } else if ("Income".equals(transaction.getType())) {
                totalIncome += amount;
                incomeByCategory.put(category, incomeByCategory.getOrDefault(category, 0.0) + amount);
            }
        }

        setupPieChart(expenditurePieChart, expenditureByCategory);
        setupPieChart(incomePieChart, incomeByCategory);

        // Update total labels
        expenditureLabel.setText(String.format("$ %.2f", totalExpenditure));
        incomeLabel.setText(String.format("$ %.2f", totalIncome));
        totalLabel.setText(String.format("Total: $ %.2f", totalIncome - totalExpenditure));

        // Set visibility based on data
        expenditureChartContainer.setVisible(totalExpenditure > 0);
        expenditureChartContainer.setManaged(totalExpenditure > 0);
        incomeChartContainer.setVisible(totalIncome > 0);
        incomeChartContainer.setManaged(totalIncome > 0);
    }

    private void setupPieChart(PieChart pieChart, Map<String, Double> dataMap) {
        ObservableList<Data> chartData = FXCollections.observableArrayList();
        dataMap.forEach((category, amount) -> chartData.add(new PieChart.Data(category, amount)));
        pieChart.setData(chartData);

        for (PieChart.Data data : chartData) {
            Node pieSlice = data.getNode();
            final Tooltip tooltip = new Tooltip();
            tooltip.setText(String.format("%s: $%.2f", data.getName(), data.getPieValue()));
            // We will use the following to adjust the display delay and duration of the tooltip.
            tooltip.setShowDelay(Duration.seconds(0.1)); // Short delay before showing the tooltip
            tooltip.setHideDelay(Duration.seconds(0));   // Hide immediately when mouse exits

            pieSlice.setOnMouseEntered(e -> {
                pieSlice.setStyle("-fx-border-color: black; -fx-border-width: 3;");
                // Set the position of the tooltip based on the mouse position
                double tooltipX = e.getScreenX() + 35; // Offset to the right of the mouse
                double tooltipY = e.getScreenY() - 35; // Offset above the mouse
                tooltip.show(pieSlice, tooltipX, tooltipY);
            });
            pieSlice.setOnMouseExited(e -> {
                pieSlice.setStyle("");
                tooltip.hide(); // Hide the tooltip when the mouse exits the slice
            });
        }
    }



    @FXML
    private void navigateToMainView() {
        loadScene("/org/example/fineasy/mainView.fxml", btnBack);
    }
}
