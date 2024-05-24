package pt.isec.pa.javalife.ui.gui.panes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenuUI {
    private Scene scene;
    private Stage primaryStage;

    private Button btnNewSimulation;

    private Button btnLoadSimulation;

    private Button btnSettings;

   private Button btnExit;

    public MainMenuUI(Stage primaryStage) {
        this.primaryStage = primaryStage;
        createViews();
        registerHandlers();
    }

    private void createViews() {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        btnNewSimulation = new Button("New Simulation");
        btnLoadSimulation = new Button("Load Simulation");
        btnSettings = new Button("Settings");
        btnExit = new Button("Exit");

        root.getChildren().addAll(btnNewSimulation, btnLoadSimulation, btnSettings, btnExit);

        scene = new Scene(root, 800, 600);



    }

    private void registerHandlers(){
        btnExit.setOnAction(event -> primaryStage.close());
    }

    public Scene getScene() {
        return scene;
    }
}
