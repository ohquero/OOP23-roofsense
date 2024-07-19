package roofsense.boundary.javafxview;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class DashboardJavafxView extends JavafxView {

    @FXML
    private VBox rootNode;

    public Node getRootNode() {
        return rootNode;
    }

}