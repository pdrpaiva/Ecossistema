package pt.isec.pa.javalife.ui.gui.scenes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
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
        HBox root = new HBox();
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #01270E;");
        root.setSpacing(20); // Ajustar o espaçamento entre os elementos

        // Adicionando uma classe CSS específica para MainMenuUI
        root.getStyleClass().add("MainMenuUI");

        // Adicionando uma imagem
        ImageView imageView = new ImageView(new Image(getClass().getResource("/pt/isec/pa/javalife/ui/gui/resources/images/teste.png").toExternalForm()));
        imageView.setFitWidth(350); // Ajustando o tamanho da imagem
        imageView.setPreserveRatio(true);

        // Criando uma VBox para os botões
        VBox buttonContainer = new VBox(10); // Diminuir o espaçamento entre os botões
        buttonContainer.setAlignment(Pos.CENTER);

        btnNewSimulation = new Button("NEW SIMULATION");
        btnLoadSimulation = new Button("LOAD SIMULATION");
        btnSettings = new Button("SETTINGS");
        btnExit = new Button("EXIT");

        buttonContainer.getChildren().addAll(btnNewSimulation, btnLoadSimulation, btnSettings, btnExit);

        // Ajustar largura do buttonContainer para ser igual à largura do logo
        buttonContainer.setMinWidth(300);

        // Adicionando a imagem e o container de botões ao root
        root.getChildren().addAll(imageView, buttonContainer);

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
