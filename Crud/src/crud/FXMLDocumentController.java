package crud;

import crudDatabase.CrudDatabase;
import crud.MySQLConnectionAccounts;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FXMLDocumentController {

    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private Button LoginButton;

    @FXML
    private PasswordField Password;

    @FXML
    private TextField Username;

    @FXML
    private ImageView WowImage;

    @FXML
    private Label label;

    @FXML
    private AnchorPane canvas;

    @FXML
    private Text text;

    @FXML
    private GridPane textGrid;

    @FXML
    public void initialize() {
        // Load image
        Image image = new Image(getClass().getResourceAsStream("wow.png"));
        WowImage.setImage(image);

        // Style login button
        LoginButton.setStyle("-fx-cursor: hand;");

        // Listen for Enter key press on the password field to trigger login
        Password.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleLogin(new ActionEvent());  // Call handleLogin as if the login button was pressed
            }
        });
        
    }

    @FXML
    void handleLogin(ActionEvent event) {
        String username = Username.getText();
        String password = Password.getText();

        // Check if either field is blank
        if (username.isEmpty() || password.isEmpty()) {
            text.setText("Please enter both username and password.");
            text.setStyle("-fx-fill: red;");  // Set text color to red for error
            System.out.println("Username or password is empty.");
        } else {
            // Proceed with authentication
            if (authenticateUser(username, password)) {
                text.setText("Login successful!");
                text.setStyle("-fx-fill: green;");  // Set text color to green for success
                System.out.println("Login successful!");

                // Load the main application screen
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/crudDatabase/CrudDatabase.fxml"));
                    AnchorPane root = loader.load();
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) LoginButton.getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                text.setText("Invalid username or password.");
                text.setStyle("-fx-fill: red;");
                System.out.println("Invalid username or password.");
            }
        }
    }

    // Method to authenticate the user from the database
    private boolean authenticateUser(String username, String password) {
        String query = "SELECT * FROM users WHERE BINARY username = ? AND password = ?;";

        try (Connection conn = MySQLConnectionAccounts.connect(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            return rs.next();  // If a matching row is found, return true (valid login)
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean validateLogin(String username, String password) {
        // Replace with your actual validation logic (e.g., check against a database)
        return "user".equals(username) && "password".equals(password);
    }

}
