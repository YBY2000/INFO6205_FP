package org.example.fineasy.Utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class ShowDialog {
    /**
     * Shows an alert dialog with the given title and content.
     *
     * @param title   the title of the dialog
     * @param content the content message of the dialog
     * @param type    the type of the alert dialog
     */
    public static void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Shows a confirmation dialog with "OK" and "Cancel" buttons.
     *
     * @param title   the title of the dialog
     * @param content the content message of the dialog
     * @return true if the user clicked "OK", false otherwise
     */
    public static boolean showConfirmationDialog(String title, String content) {
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle(title);
        confirmationDialog.setHeaderText(null);
        confirmationDialog.setContentText(content);

        Optional<ButtonType> result = confirmationDialog.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}
