package roofsense.view.javafxview;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class DevicesOverviewJavafxView extends JavafxView {

    @FXML
    private VBox rootNode;

    public void initialize() {
    }

    @Override
    public Node getRootNode() {
        return rootNode;
    }
}
