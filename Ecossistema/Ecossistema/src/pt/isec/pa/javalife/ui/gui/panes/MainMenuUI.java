package pt.isec.pa.javalife.ui.gui.panes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pt.isec.pa.javalife.model.facade.JavaLifeFacade;

public class MainMenuUI extends VBox {
    private final JavaLifeFacade facade;
    private final Stage primaryStage;

    public MainMenuUI(JavaLifeFacade facade, Stage primaryStage) {
        this.facade = facade;
        this.primaryStage = primaryStage;

        Button btnNewSimulation = new Button("New Simulation");
        Button btnLoadSimulation = new Button("Load Simulation");
        Button btnSettings = new Button("Settings");
        Button btnExit = new Button("Exit");

        btnNewSimulation.setOnAction(event -> startNewSimulation());
        btnLoadSimulation.setOnAction(event -> loadSimulation());
        btnSettings.setOnAction(event -> openSettings());
        btnExit.setOnAction(event -> primaryStage.close());

        this.setAlignment(Pos.CENTER);
        this.setSpacing(10);
        this.getChildren().addAll(btnNewSimulation, btnLoadSimulation, btnSettings, btnExit);
    }

    private void startNewSimulation() {
        EcossistemaUI ecossistemaUI = new EcossistemaUI(facade);
        Scene scene = new Scene(ecossistemaUI, 800, 600);
        primaryStage.setScene(scene);
    }

    private void loadSimulation() {
        // Lógica para carregar uma simulação existente
    }

    private void openSettings() {
        SettingsUI settingsUI = new SettingsUI(facade, primaryStage);
        Scene scene = new Scene(settingsUI, 800, 600);
        primaryStage.setScene(scene);
    }
}
