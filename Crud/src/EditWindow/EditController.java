package EditWindow;

import crudDatabase.User;

import crudDatabase.CrudDataBaseController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import crudDatabase.MySQLConnection;
import crudDatabase.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EditController {

    private CrudDataBaseController editController;

    public void setMainController(CrudDataBaseController mainController) {
        this.editController = mainController;
    }

    @FXML
    private TextField AgeField;

    @FXML
    private TextField EmailField;

    @FXML
    private Text Invalid;

    @FXML
    private Button backButton;

    @FXML
    private Button confirmButton;

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    private User user;

    @FXML

    public void setUserData(User user) {
        this.user = user;
        idField.setText(String.valueOf(user.getId()));  // Display ID (non-editable)
        nameField.setText(user.getName());
        EmailField.setText(user.getEmail());
        AgeField.setText(String.valueOf(user.getAge()));

        idField.setEditable(false);
        idField.setDisable(true);
    }

    @FXML
    void handleCancelButtonClick(ActionEvent event) {
        // Close the Edit window without saving
        ((Stage) backButton.getScene().getWindow()).close();
    }

    @FXML
    void handleConfirmButtonClick(ActionEvent event) {
        // Clear any previous error messages
        Invalid.setText("");

        // Validate and save the edited data
        String name = nameField.getText();
        String email = EmailField.getText();
        String ageText = AgeField.getText();

        // Check for empty fields
        if (name.isEmpty() || email.isEmpty() || ageText.isEmpty()) {
            Invalid.setStyle("-fx-fill: red;");
            Invalid.setText("Please fill in all fields.");
            return;
        }

        // Email validation using regex
        String emailPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if (!email.matches(emailPattern)) {
            Invalid.setStyle("-fx-fill: red;");
            Invalid.setText("Invalid Email!");
            return;
        }

        // Show a confirmation alert
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Edit");
        confirmationAlert.setHeaderText("Are you sure you want to save the changes?");
        confirmationAlert.setContentText("Click OK to confirm or Cancel to return.");

        // Wait for the user's response
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // If the user clicks OK, update the user object
            user.setName(name);
            user.setEmail(email);
            user.setAge(Integer.parseInt(ageText));

            // Update the user in the database
            updateUserInDatabase(user);

            // Close the Edit window
            ((Stage) confirmButton.getScene().getWindow()).close();
        } else {
            // If the user clicks Cancel, do nothing
            System.out.println("Edit operation canceled.");
        }
    }

    private void updateUserInDatabase(User user) {
        String updateQuery = "UPDATE users SET name = ?, email = ?, age = ? WHERE id = ?";

        try (Connection conn = MySQLConnection.connect(); PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setInt(3, user.getAge());
            pstmt.setInt(4, user.getId());
            pstmt.executeUpdate();

            System.out.println("User updated: " + user.getName());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
