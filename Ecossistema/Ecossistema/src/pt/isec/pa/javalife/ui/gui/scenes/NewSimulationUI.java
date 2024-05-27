package pt.isec.pa.javalife.ui.gui.scenes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class NewSimulationUI {
    private Scene scene;
    private Stage primaryStage;

    public NewSimulationUI(Stage primaryStage) {
        this.primaryStage = primaryStage;
        createViews();
        registerHandlers();
    }

    private void createViews() {
        GridPane root = new GridPane();
        root.setStyle("-fx-background-color: #4B0082;");
        root.setHgap(20);
        root.setVgap(20);
        root.setAlignment(Pos.CENTER);

        // Top Section
        Label createLabel = new Label("Comece do Zero: Crie Seu Próprio Ecossistema");
        createLabel.setTextFill(Color.WHITE);
        createLabel.setFont(new Font(24));
        Button createButton = new Button("Criar");

        HBox topSection = new HBox(20, createLabel, createButton);
        topSection.setAlignment(Pos.CENTER);

        // Bottom Section
        Label importLabel = new Label("Traga Diversidade: Importe um Ecossistema Existente");
        importLabel.setTextFill(Color.WHITE);
        importLabel.setFont(new Font(24));
        Button importButton = new Button("Importar");

        HBox bottomSection = new HBox(20, importLabel, importButton);
        bottomSection.setAlignment(Pos.CENTER);

        // Add sections to root
        root.add(topSection, 0, 0);
        root.add(bottomSection, 0, 1);

        scene = new Scene(root, 800, 600);
    }

    private void registerHandlers() {
        Button createButton = (Button) ((HBox) ((GridPane) scene.getRoot()).getChildren().get(0)).getChildren().get(1);
        createButton.setOnAction(event -> {
            CreateEcosystemUI createEcosystem = new CreateEcosystemUI(primaryStage);
            primaryStage.setScene(createEcosystem.getScene());
        });
    }

    public Scene getScene() {
        return scene;
    }
}
