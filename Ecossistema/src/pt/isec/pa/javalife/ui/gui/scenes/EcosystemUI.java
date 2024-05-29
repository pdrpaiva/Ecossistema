package pt.isec.pa.javalife.ui.gui.scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EcosystemUI {
    private Scene scene;
    private Stage primaryStage;

    public EcosystemUI(Stage primaryStage) {
        this.primaryStage = primaryStage;
        createViews();
        registerHandlers();
    }

    private void createViews() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #00593c;");

        // Top toolbar
        HBox toolbar = new HBox(10);
        toolbar.setPadding(new Insets(10));
        toolbar.setAlignment(Pos.CENTER_LEFT);
        toolbar.setStyle("-fx-background-color: #00593c;");

        Button btnPause = new Button("",new ImageView(new Image("file:gui/resources/wolf.png")));

        Button btnRestart = new Button("",new ImageView(new Image("file:gui/resources/wolf.png")));
        Button btnSettings = new Button("",new ImageView(new Image("file:resources/wolf.png")));

        toolbar.getChildren().addAll(btnPause, btnRestart, btnSettings);
        root.setTop(toolbar);

        // Área central do ecossistema
        VBox ecosystemDisplay = new VBox();
        ecosystemDisplay.setPadding(new Insets(20));
        ecosystemDisplay.setAlignment(Pos.CENTER);
        ecosystemDisplay.setStyle("-fx-background-color: #036c4c;");

        //ImageView ecosystemImageView = new ImageView(new Image("../resources/wolf.png"));
        //ecosystemImageView.setFitWidth(50);
        //ecosystemImageView.setFitHeight(50);
        // ecosystemDisplay.getChildren().add(ecosystemImageView);

        root.setCenter(ecosystemDisplay);

        // Right sidebar for controls
        VBox sidebar = new VBox(10);
        sidebar.setPadding(new Insets(10));
        sidebar.setStyle("-fx-background-color: #00593c;");
        sidebar.setAlignment(Pos.TOP_CENTER);

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Fauna", "Flora", "Inanimado");
        comboBox.setValue("Fauna");

        Button btnAddElement = new Button("Adicionar Elemento");
        Button btnCreateEcosystem = new Button("Criar Ecossistema");
        Button btnImport = new Button("Importar");
        Button btnExport = new Button("Exportar");

        sidebar.getChildren().addAll(comboBox, btnAddElement, btnCreateEcosystem, btnImport, btnExport);
        root.setRight(sidebar);

        scene = new Scene(root, 800, 600);
    }

    private void registerHandlers() {
        // Add event handlers if needed
    }

    public Scene getScene() {
        return scene;
    }
}
