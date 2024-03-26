package org.example.fineasy.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.chart.PieChart.Data;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import org.example.fineasy.HelloController;
import org.example.fineasy.models.DataManagementSingleton;
import org.example.fineasy.models.Transaction;

import java.io.IOException;

public class VisualizationController {

    @FXML
    private DatePicker datePicker;

    @FXML
    private PieChart categoryPieChart;

    @FXML
    private PieChart expenditurePieChart;

    @FXML
    private PieChart incomePieChart;

    @FXML
    private Label expenditureLabel;

    @FXML
    private Label incomeLabel;

    @FXML
    private Label totalLabel;

    @FXML
    public void initialize() {
        // Initialize pie charts with some mock data
        populatePieChart(categoryPieChart, new Data("Category 1", 20), new Data("Category 2", 30), new Data("Category 3", 50));
        populatePieChart(expenditurePieChart, new Data("Expenses", 75));
        populatePieChart(incomePieChart, new Data("Income", 25));

        // Update labels
        expenditureLabel.setText("$ 900.00");
        incomeLabel.setText("$ 300.00");
        totalLabel.setText("Total: $ -600.00");
    }


//    public void refreshData() {
//        // Clear existing data
//        categoryPieChart.getData().clear();
//        expenditurePieChart.getData().clear();
//        incomePieChart.getData().clear();
//
//        // Fetch updated data and populate charts
//        updatePieCharts(); // Assume this method repopulates the charts with the latest data
//    }
//
//    // Then, in HelloController, when switching to the visualization view:
//    @FXML
//    public void handleVisualizationButtonClick() {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/fineasy/visualView.fxml"));
//            Parent visualizationViewRoot = loader.load();
//
//            // Get the controller and call refreshData
//            VisualizationController visualizationController = loader.getController();
//            visualizationController.refreshData();
//
//            Scene scene = new Scene(visualizationViewRoot);
//            Stage stage = (Stage) addButton.getScene().getWindow();
//            stage.setScene(scene);
//        } catch (IOException e) {
//            e.printStackTrace();
//            // Handle exception
//        }
//    }



    private void populatePieChart(PieChart pieChart, Data... data) {
        pieChart.getData().addAll(data);
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
            System.err.println("Error navigating to the main view: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBackButtonAction(ActionEvent event) {
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
            System.err.println("Error navigating to the main view: " + e.getMessage());
            e.printStackTrace();
            // Error handling, for example showing a dialog box to notify the user of the error
        }
    }
}

