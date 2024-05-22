package pt.isec.pa.javalife.ui.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.isec.pa.javalife.ui.gui.panes.RootPane;

public class JavaLifeFX extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        RootPane rootPane = new RootPane(); // Instanciando o RootPane
        Scene scene = new Scene(rootPane, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Ecossistema");
        stage.show();
    }

}

