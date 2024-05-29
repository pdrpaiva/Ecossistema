package pt.isec.pa.javalife.ui.gui.scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import pt.isec.pa.javalife.model.data.ecosystem.EcossistemaManager;
import pt.isec.pa.javalife.model.data.elements.*;

public class EcosystemUI {
    private Scene scene;
    private Stage primaryStage;
    private EcossistemaManager ecossistemaManager;
    private Pane ecosystemPane;

    public EcosystemUI(Stage primaryStage, EcossistemaManager ecossistemaManager) {
        this.primaryStage = primaryStage;
        this.ecossistemaManager = ecossistemaManager;
        createViews();
        registerHandlers();
        updateEcosystemDisplay();
    }

    private void createViews() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #00593c;");

        // Top toolbar
        HBox toolbar = new HBox(10);
        toolbar.setPadding(new Insets(10));
        toolbar.setAlignment(Pos.CENTER_LEFT);
        toolbar.setStyle("-fx-background-color: #00593c;");

        Button btnPause = new Button("Pause");
        Button btnRestart = new Button("Restart");
        Button btnSettings = new Button("Settings");

        toolbar.getChildren().addAll(btnPause, btnRestart, btnSettings);
        root.setTop(toolbar);

        // Área central do ecossistema
        ecosystemPane = new Pane();
        ecosystemPane.setPadding(new Insets(20));
        ecosystemPane.setStyle("-fx-background-color: #036c4c;");
        root.setCenter(ecosystemPane);

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

    private void updateEcosystemDisplay() {
        ecosystemPane.getChildren().clear();

        for (IElemento elemento : ecossistemaManager.getEcossistema().obterElementos()) {
            Rectangle rect;
            if (elemento instanceof Fauna) {
                rect = new Rectangle(elemento.getArea().direita() - elemento.getArea().esquerda(),
                        elemento.getArea().baixo() - elemento.getArea().cima(), Color.RED);
            } else if (elemento instanceof Flora) {
                rect = new Rectangle(elemento.getArea().direita() - elemento.getArea().esquerda(),
                        elemento.getArea().baixo() - elemento.getArea().cima(), Color.GREEN);
            } else if (elemento instanceof Inanimado) {
                rect = new Rectangle(elemento.getArea().direita() - elemento.getArea().esquerda(),
                        elemento.getArea().baixo() - elemento.getArea().cima(), Color.GRAY);
            } else {
                continue;
            }

            rect.setX(elemento.getArea().esquerda());
            rect.setY(elemento.getArea().cima());

            ecosystemPane.getChildren().add(rect);
        }
    }

    public Scene getScene() {
        return scene;
    }
}
