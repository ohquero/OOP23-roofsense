package roofsense;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import roofsense.view.javafxview.JavafxViews;

/**
 * Application entrypoint.
 */
public class Launcher extends Application {

    @Override
    public final void start(final Stage primaryStage) {
        final var rootView = JavafxViews.getRootView();
        final Parent root = (Parent) rootView.getRootNode();
        final Scene scene = new Scene(root);

        // Set scene size to the size of the screen if the scene is larger than the screen, otherwise set it to the scene's preferred size.
        final var screenBounds = Screen.getPrimary().getVisualBounds();
        if (root.prefWidth(-1) > screenBounds.getWidth() || root.prefHeight(-1) > screenBounds.getHeight()) {
            primaryStage.setMaximized(true);
        }

        primaryStage.setTitle("RoofSense");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
