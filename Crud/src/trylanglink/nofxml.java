package trylanglink;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class nofxml {

    public void showWindow() {
        try {
            // Load the FXML file for the nofxml window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("nofxml.fxml"));

            // Load the parent root from the FXML file
            Parent root = loader.load();

            // Get the controller of the nofxml window (optional)
            NofxmlController controller = loader.getController();

            // Create the stage for the nofxml window
            Stage stage = new Stage();
            stage.setTitle("NoFXML Window");
            stage.setResizable(false);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("CRUD.png")));
            // Create the scene with the root loaded from the FXML file
            Scene scene = new Scene(root); // Set size or customize
            stage.setScene(scene);

            // Show the stage (the nofxml window)
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
