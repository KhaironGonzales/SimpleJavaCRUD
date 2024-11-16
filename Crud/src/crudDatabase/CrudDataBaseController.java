package crudDatabase;

import AddWindow.Add;
import java.sql.PreparedStatement;
import AddWindow.AddFXMLController;
import EditWindow.EditController;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.animation.PauseTransition;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class CrudDataBaseController {

    @FXML
    private Button addBut;

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, Integer> colId;

    @FXML
    private TableColumn<User, String> colName;

    @FXML
    private TableColumn<User, String> colEmail;

    @FXML
    private TableColumn<User, Integer> colAge;

    @FXML
    private Button delBut;

    @FXML
    private Button editBut;

    @FXML
    private Text text;

    @FXML
    private ImageView addImg;

    @FXML
    private Button Logout;

    @FXML
    private ImageView LogoutBut;

    @FXML
    private ImageView EditImg;

    @FXML
    private ImageView delImg;

    public void initialize() {

        userTable.setOnMouseClicked(event -> {
            // Check if the click is outside any row
            if (userTable.getSelectionModel().getSelectedItem() != null) {
                // If click occurs on an empty area (i.e., not on any row)
                if (event.getTarget() instanceof TableView) {
                    userTable.getSelectionModel().clearSelection(); // Deselect the selected item
                }
            }
        });
        // Set up the columns to bind to the User properties
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));

        // Fetch and populate data
        ObservableList<User> data = fetchDataFromDatabase();
        userTable.setItems(data);

        // Load images and handle any missing images
        loadImages();

        // Set cursor style for buttons
        addBut.setStyle("-fx-cursor: hand;");
        delBut.setStyle("-fx-cursor: hand;");
        editBut.setStyle("-fx-cursor: hand;");

        userTable.requestFocus();
        addBut.setFocusTraversable(false);
        editBut.setFocusTraversable(false);
        delBut.setFocusTraversable(false);
        userTable.setFocusTraversable(false);
        Logout.setFocusTraversable(false);

        // Handle mouse click to deselect a user when clicking outside rows
    }

    private void loadImages() {
        try {
            Image add = new Image(getClass().getResourceAsStream("Icons/Add.png"));
            addImg.setImage(add);
        } catch (Exception e) {
            System.out.println("Error loading Add.png: " + e.getMessage());
        }

        try {
            Image edit = new Image(getClass().getResourceAsStream("Icons/Edit.png"));
            EditImg.setImage(edit);
        } catch (Exception e) {
            System.out.println("Error loading Edit.png: " + e.getMessage());
        }

        try {
            Image del = new Image(getClass().getResourceAsStream("Icons/delete.png"));
            delImg.setImage(del);
        } catch (Exception e) {
            System.out.println("Error loading delete.png: " + e.getMessage());
        }
        try {
            Image logout = new Image(getClass().getResourceAsStream("Icons/Logout.png"));
            LogoutBut.setImage(logout);
        } catch (Exception e) {
            System.out.println("Error loading delete.png: " + e.getMessage());
        }
    }

    public void refreshTableView() {
        ObservableList<User> data = fetchDataFromDatabase();
        userTable.setItems(data);
    }

    private ObservableList<User> fetchDataFromDatabase() {
        ObservableList<User> data = FXCollections.observableArrayList();

        try (Connection conn = MySQLConnection.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT id, name, email, age FROM users")) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                int age = rs.getInt("age");
                data.add(new User(id, name, email, age));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    @FXML
    private void handleAddButtonClick(ActionEvent event) {
        try {
            FXMLLoader AddfxmlLoader = new FXMLLoader(getClass().getResource("/AddWindow/AddFXML.fxml"));
            Parent root = AddfxmlLoader.load();

            // Get the AddFXMLController and pass the CrudDataBaseController reference
            AddFXMLController addController = AddfxmlLoader.getController();
            addController.setMainController(this);

            // Open the new stage for Add.fxml
            Stage addStage = new Stage();
            addStage.setResizable(false);
            addStage.setScene(new Scene(root));
            addStage.setTitle("Add A User");
            addStage.initModality(Modality.APPLICATION_MODAL);
            addStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
   
    }
    

    @FXML
    private void handleEditButton(ActionEvent event) {
//        try {
//            FXMLLoader EditfxmlLoader = new FXMLLoader(getClass().getResource("/EditWindow/Edit.fxml"));
//            Parent root = EditfxmlLoader.load();
//
//            // Get the AddFXMLController and pass the CrudDataBaseController reference
//            EditController addController = EditfxmlLoader.getController();
//            addController.setMainController(this);
//
//            // Open the new stage for Add.fxml
//            Stage addStage = new Stage();
//            addStage.setScene(new Scene(root));
//            addStage.setTitle("Edit User");
//            addStage.initModality(Modality.APPLICATION_MODAL);
//            addStage.show();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        // Get the selected user from the TableView
        // Check if a user is selected
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            text.setText("Please select a user to edit");
            System.out.println("Please select a user to edit");

            PauseTransition pause = new PauseTransition(Duration.seconds(3)); // 3 seconds delay
            pause.setOnFinished(e -> text.setText("")); // Clear the message after 3 seconds
            pause.play();  // Start the pause transition

            return;
        }

        try {
            // Load Edit.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditWindow/Edit.fxml"));
            Parent editRoot = loader.load();

            // Get the EditController and pass the selected user
            EditController editController = loader.getController();
            editController.setUserData(selectedUser);

            // Create a new stage for the Edit window
            Stage editStage = new Stage();
            editStage.setResizable(false);
            editStage.setTitle("Edit User");
            editStage.setScene(new Scene(editRoot));

            // Make the window modal to block interaction with CrudDatabase.fxml
            editStage.initModality(Modality.WINDOW_MODAL);
            editStage.initOwner(((Node) event.getSource()).getScene().getWindow());

            // Show the Edit window and wait until it's closed
            editStage.showAndWait();
            
            // Refresh the TableView after editing (in case of changes)
            refreshTableView();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteButtonClick(ActionEvent event) {
        // Get the selected user from the TableView
        User selectedUser = userTable.getSelectionModel().getSelectedItem();

        if (selectedUser == null) {
            // No user selected, show an alert or message
            text.setText("Please select a user to delete");
            System.out.println("Please select a user to delete");

            // Create a PauseTransition to clear the message after 3 seconds
            PauseTransition pause = new PauseTransition(Duration.seconds(3)); // 3 seconds delay
            pause.setOnFinished(e -> text.setText("")); // Clear the message after 3 seconds
            pause.play();  // Start the pause transition

            return;
        }

        // Show a confirmation dialog before deleting
        Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Deletion");
        confirmationAlert.setHeaderText("Are you sure you want to delete the selected user?");
        confirmationAlert.setContentText("This action cannot be undone.");

        // Show the dialog and wait for the user's response
        ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

        // If the user clicks "OK" (Yes), proceed with deletion
        if (result == ButtonType.OK) {
            // Delete the user from the database
            deleteUserFromDatabase(selectedUser);

            // Refresh the TableView to reflect the deletion
            refreshTableView();
        } else {
            // If the user clicks "Cancel" (No), just print a message (optional)
            System.out.println("User deletion canceled.");
        }
    }

    private void deleteUserFromDatabase(User user) {
        String deleteQuery = "DELETE FROM users WHERE id = ?";

        try (Connection conn = MySQLConnection.connect(); PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {
            pstmt.setInt(1, user.getId());
            pstmt.executeUpdate();

            System.out.println("User deleted: " + user.getName());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Your logout button handler
    @FXML
    private void handleLogoutButtonClick(ActionEvent event) {
        // Create a confirmation dialog
        Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Logout");
        confirmationAlert.setHeaderText("Are you sure you want to log out?");
        confirmationAlert.setContentText("You will be returned to the login screen.");

        // Wait for the user's response
        ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

        // If the user confirms (clicks "OK")
        if (result == ButtonType.OK) {
            // Close the current window
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

            // Open the login window
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/crud/FXMLDocument.fxml"));
                Parent root = loader.load();
                Stage loginStage = new Stage();
                loginStage.setTitle("Login");
                loginStage.setScene(new Scene(root));

                // Show the login stage
                loginStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // If the user cancels the logout, do nothing
            System.out.println("Logout canceled.");
        }
    }
}
