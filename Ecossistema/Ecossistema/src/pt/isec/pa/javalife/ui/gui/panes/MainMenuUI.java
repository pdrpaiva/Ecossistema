package pt.isec.pa.javalife.ui.gui.panes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #01270E;");

        // Adicionando uma imagem
        ImageView imageView = new ImageView(new Image(getClass().getResource("/pt/isec/pa/javalife/ui/gui/resources/images/logo2.png").toExternalForm()));
        imageView.setFitWidth(400); // Aumentando o tamanho da imagem
        imageView.setPreserveRatio(true);

        // Adicionando imageView ao root
        root.getChildren().add(imageView);

        btnNewSimulation = new Button("New Simulation");
        btnLoadSimulation = new Button("Load Simulation");
        btnSettings = new Button("Settings");
        btnExit = new Button("Exit");

        root.getChildren().addAll(btnNewSimulation, btnLoadSimulation, btnSettings, btnExit);

        // Carregar a folha de estilo CSS
        scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/pt/isec/pa/javalife/ui/gui/resources/css/styles.css").toExternalForm());
    }

    private void registerHandlers() {
        btnNewSimulation.setOnAction(event -> {
             NewSimulationUI newSimulationUI = new NewSimulationUI(primaryStage);
             primaryStage.setScene(newSimulationUI.getScene());
        });

        btnLoadSimulation.setOnAction(event -> {
            // Handle load simulation action
        });

        btnSettings.setOnAction(event -> {
            // Handle settings action
        });

        btnExit.setOnAction(event -> primaryStage.close());
    }

    public Scene getScene() {
        return scene;
    }
}
