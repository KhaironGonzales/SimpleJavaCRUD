package crudDatabase;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class trylang extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Load the FXML file for the layout
        Parent root = FXMLLoader.load(getClass().getResource("/trylang/trylang.fxml"));

        // Create the scene with the loaded root
        Scene scene = new Scene(root);

        // Set the scene on the primary stage
        primaryStage.setScene(scene);

        // Set a title for the window (optional)
        primaryStage.setTitle("Trylang Application");

        // Display the stage
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }
}
