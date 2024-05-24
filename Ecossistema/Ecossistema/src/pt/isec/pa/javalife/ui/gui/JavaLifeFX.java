package pt.isec.pa.javalife.ui.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pt.isec.pa.javalife.ui.gui.panes.InitialMenuUI;
import pt.isec.pa.javalife.ui.gui.panes.RootPane;

public class JavaLifeFX extends Application {


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaLife");

        InitialMenuUI initialMenu = new InitialMenuUI(primaryStage);
        primaryStage.setScene(initialMenu.getScene());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

