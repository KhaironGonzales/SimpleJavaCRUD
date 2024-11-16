package trylang;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import trylanglink.nofxml;

public class TrylangController {

    @FXML
    private Button nofxml;

    @FXML
    private void handleNofxmlButtonClick(ActionEvent event) {
        // Create an instance of the nofxml class and call its method to open the window
        nofxml nofxmlWindow = new nofxml();
        nofxmlWindow.showWindow(); // This method will be implemented in nofxml.java
    }

}
