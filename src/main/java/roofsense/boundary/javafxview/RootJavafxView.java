package roofsense.boundary.javafxview;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class RootJavafxView extends JavafxView {

    @FXML
    private BorderPane rootNode;

    @FXML
    private Button showDevicesOverviewButton;

    public void initialize() {
        // Set the center of the root view to the devices overview when the button is clicked.
        showDevicesOverviewButton.setOnAction(event -> rootNode.setCenter(JavafxViews.getDevicesOverview().getRootNode()));
    }

    @Override
    public Node getRootNode() {
        return rootNode;
    }
}
