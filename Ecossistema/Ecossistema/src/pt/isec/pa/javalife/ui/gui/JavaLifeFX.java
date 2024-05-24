package pt.isec.pa.javalife.ui.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pt.isec.pa.javalife.ui.gui.panes.RootPane;

public class JavaLifeFX extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaLife");
        System.out.println("goiaba");

        StackPane initialPane = new StackPane();
        Text pressAnyKeyText = new Text("Press any key to start");
        initialPane.getChildren().add(pressAnyKeyText);

        Scene initialScene = new Scene(initialPane, 800, 600);

        initialScene.setOnKeyPressed(event -> {
            if (event.getCode() != KeyCode.UNDEFINED) {
                RootPane rootPane = new RootPane();
                Scene mainScene = new Scene(rootPane, 800, 600);
                primaryStage.setScene(mainScene);
            }
        });

        primaryStage.setScene(initialScene);
        primaryStage.show();
    }

}

