package pt.isec.pa.javalife.ui.gui.scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
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
        HBox root = new HBox();
        root.getStyleClass().add("NewSimulationUI");

        // Left Section (Create New Ecosystem)
        VBox leftSection = new VBox(20);
        leftSection.getStyleClass().add("left-section");
        leftSection.setAlignment(Pos.CENTER);
        leftSection.setPadding(new Insets(20));
        HBox.setHgrow(leftSection, Priority.ALWAYS); // Ensure left section grows

        Label createTitle = new Label("Começar do Zero");
        createTitle.getStyleClass().add("title");
        Label createDescription = new Label("Este modo possibilita a criação e configuração\n do próprio ecossistema.");
        createDescription.getStyleClass().add("description");
        Button createButton = new Button("Criar");
        createButton.getStyleClass().add("new-simulation-button");

        leftSection.getChildren().addAll(createTitle, createDescription, createButton);

        // Right Section (Import Existing Ecosystem)
        VBox rightSection = new VBox(20);
        rightSection.getStyleClass().add("right-section");
        rightSection.setAlignment(Pos.CENTER);
        rightSection.setPadding(new Insets(20));
        HBox.setHgrow(rightSection, Priority.ALWAYS); // Ensure right section grows

        Label importTitle = new Label("Importar um Ecossistema");
        importTitle.getStyleClass().add("title");
        Label importDescription = new Label("Este modo permite importar um ecossistema \npreviamente criado e já configurado.");
        importDescription.getStyleClass().add("description");
        Button importButton = new Button("Importar");
        importButton.getStyleClass().add("new-simulation-button2");

        rightSection.getChildren().addAll(importTitle, importDescription, importButton);

        // Add sections to root
        root.getChildren().addAll(leftSection, rightSection);

        scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/pt/isec/pa/javalife/ui/gui/resources/css/NewSimulation.css").toExternalForm());
    }

    private void registerHandlers() {
        Button createButton = (Button) ((VBox) ((HBox) scene.getRoot()).getChildren().get(0)).getChildren().get(2);
        createButton.setOnAction(event -> {
            CreateEcosystemUI createEcosystem = new CreateEcosystemUI(primaryStage);
            primaryStage.setScene(createEcosystem.getScene());
        });

        Button importButton = (Button) ((VBox) ((HBox) scene.getRoot()).getChildren().get(1)).getChildren().get(2);
        importButton.setOnAction(event -> {
            // Implementar ação de importação
        });
    }

    public Scene getScene() {
        return scene;
    }
}
