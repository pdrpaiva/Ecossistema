package pt.isec.pa.javalife.ui.gui.panes;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class MainMenuUI extends HBox {
    public MainMenuUI(){
        Button btnStart = new Button("Start");
        Button btnPause = new Button("Pause");
        Button btnStop = new Button("Stop");

        btnStart.setOnAction(event -> {
            // Lógica para iniciar a simulação
        });

        btnPause.setOnAction(event -> {
            // Lógica para pausar a simulação
        });

        btnStop.setOnAction(event -> {
            // Lógica para parar a simulação
        });

        this.getChildren().addAll(btnStart,btnPause,btnStop);
    }
}
