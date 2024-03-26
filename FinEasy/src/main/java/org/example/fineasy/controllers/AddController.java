package org.example.fineasy.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.fineasy.HelloController;
import org.example.fineasy.models.DataManagementSingleton;
import org.example.fineasy.models.Transaction;

import java.io.IOException;
import java.time.LocalDate;

public class AddController {
    @FXML
    private ChoiceBox<String> choiceType;

    @FXML
    private TextField textComment;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField textAmount; // 假设你有这个输入字段
    @FXML
    private TextField textCategory; // 假设你有这个输入字段

    @FXML private ToggleButton toggleFood;
    @FXML private ToggleButton toggleEducation;
    @FXML private ToggleButton toggleTransportation;
    @FXML private ToggleButton toggleEntertainment;
    @FXML private ToggleButton toggleDaily;
    @FXML private ToggleButton toggleGift;
    private ToggleGroup categoryToggleGroup;

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

    @FXML
    private void handleSaveAction(ActionEvent event) {
        try {
            String id = generateTransactionId();
            String type = choiceType.getValue();
            double amount = Double.parseDouble(textAmount.getText().trim()); // trim() to remove leading/trailing spaces
            LocalDate date = datePicker.getValue();

            // Determine the selected category from ToggleButtons
            ToggleButton selectedCategory = (ToggleButton) categoryToggleGroup.getSelectedToggle();
            String category = selectedCategory != null ? selectedCategory.getText() : "None"; // "None" or similar default

            String comment = textComment.getText().trim(); // trim() here as well

            Transaction transaction = new Transaction(id, type, amount, date, category, comment);
            DataManagementSingleton.getInstance().addTransaction(transaction);

            System.out.println("Transaction saved successfully.");
            navigateToMainView(event);
        } catch (Exception e) { // Broader exception handling
            System.err.println("Error saving transaction: " + e.getMessage());
            e.printStackTrace();
            // Add error feedback to the user here
        }
    }

    private void navigateToMainView(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/fineasy/mainView.fxml"));
            Parent mainViewRoot = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(mainViewRoot));

            // Now trigger the update in HelloController (the main view's controller)
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
    private void handleCancelAction(ActionEvent event) {
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

    private void closeStage(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private String generateTransactionId() {
        // 实现ID生成逻辑，例如基于时间戳或其他
        return String.valueOf(System.currentTimeMillis());
    }
}
