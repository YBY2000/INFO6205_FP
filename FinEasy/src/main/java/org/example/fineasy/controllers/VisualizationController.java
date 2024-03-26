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


    @FXML
    private void handleBackButtonAction(ActionEvent event) {
        try {
            // 加载主视图FXML文件
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/fineasy/mainView.fxml"));
            Parent mainView = loader.load();

            // 获取当前窗口（Stage）并设置新场景（Scene）
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(mainView); // 将主视图设置为当前场景的根节点
        } catch (IOException e) {
            e.printStackTrace();
            // 错误处理，例如显示一个对话框通知用户错误
        }
    }
}

