package AddWindow;

import crudDatabase.CrudDataBaseController;
import crudDatabase.MySQLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AddFXMLController {

    private CrudDataBaseController mainController;

    @FXML
    private TextField nameField;

    @FXML
    private TextField EmaiField;

    @FXML
    private Text Invalid;

    @FXML
    private TextField AgeField;

    @FXML
    private Button confirmButton;

    @FXML
    private Button backButton;

    public void setMainController(CrudDataBaseController mainController) {
        this.mainController = mainController;
    }
    
    @FXML
    private AnchorPane Anchor;

    @FXML
    private void handleConfirmButtonClick() {
        String name = nameField.getText();
        String email = EmaiField.getText();
        String ageText = AgeField.getText();

        // Clear any previous error message
        Invalid.setText("");

        // Check if any of the fields are empty
        if (name.isEmpty() || email.isEmpty() || ageText.isEmpty()) {
            Invalid.setText("Please fill in all fields.");
            Invalid.setStyle("-fx-fill: red;");
            System.out.println("Please fill in all fields.");
            return;
        }

        // Check if the email format is valid
        if (!isValidEmail(email)) {
            Invalid.setText("Invalid Email!");  // Display the error message in the Text field
            return;
        }

        // Show a confirmation dialog before saving the data
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Changes");
        confirmationAlert.setHeaderText("Are you sure you want to save these details?");
//        confirmationAlert.setContentText("Name: " + name + "\nEmail: " + email + "\nAge: " + ageText);

        // Show the dialog and wait for the user's response
        ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

        // If the user clicks "OK" (Yes), proceed with saving and closing
        if (result == ButtonType.OK) {
            try {
                int age = Integer.parseInt(ageText);
                saveUserToDatabase(name, email, age);
                System.out.println("Data added successfully!");

                // Refresh the TableView in MainController
                if (mainController != null) {
                    mainController.refreshTableView();
                }

                // Close the window
                Stage stage = (Stage) confirmButton.getScene().getWindow();
                stage.close();

            } catch (NumberFormatException e) {
                System.out.println("Invalid age. Please enter a number.");
            }
        } else {
            // If the user clicks "Cancel" (No), just print a message (optional)
            System.out.println("Changes not saved.");
        }
    }

// Updated email validation method
    private boolean isValidEmail(String email) {
        // Updated regex for stricter email validation
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }

    private void saveUserToDatabase(String name, String email, int age) {
        String insertQuery = "INSERT INTO users (name, email, age) VALUES (?, ?, ?)";
        try (Connection conn = MySQLConnection.connect(); PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {

            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setInt(3, age);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error saving data to the database.");
        }
    }

    @FXML
    private void handleCancelButtonClick() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }


}
