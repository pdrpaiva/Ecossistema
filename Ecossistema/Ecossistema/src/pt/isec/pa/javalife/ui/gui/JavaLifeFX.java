package pt.isec.pa.javalife.ui.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.isec.pa.javalife.ui.gui.panes.RootPane;

public class JavaLifeFX extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaLife");

        RootPane rootPane = new RootPane();
        Scene scene = new Scene(rootPane, 800, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

}

