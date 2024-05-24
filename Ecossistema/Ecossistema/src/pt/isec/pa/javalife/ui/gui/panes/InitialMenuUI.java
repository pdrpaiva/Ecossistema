package pt.isec.pa.javalife.ui.gui.panes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class InitialMenuUI {
    private Scene scene;
    private Stage primaryStage;

    public InitialMenuUI(Stage primaryStage) {
        this.primaryStage = primaryStage;
        createViews();
        registerHandlers();
    }

    private void createViews() {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #003423;");

        // Logo Image
        //ImageView logoImageView = new ImageView(new Image("path/to/your/image.png"));
        //logoImageView.setFitWidth(150);
        //logoImageView.setPreserveRatio(true);

        // Text for Institute Information
        Label instituteLabel = new Label("Instituto Superior de Engenharia de Coimbra\n" +
                "Licenciatura em Engenharia Informática\n" +
                "Programação Avançada - 2023/2024");
        
        instituteLabel.setTextFill(Color.WHITE);
        instituteLabel.setFont(new Font(30));
        instituteLabel.setAlignment(Pos.CENTER);

        // JavaLife Title
        Text titleText = new Text("JavaLife");
        titleText.setFill(Color.WHITE);
        titleText.setFont(new Font("Arial", 48));

        // Developed by
        Label developedByLabel = new Label("Desenvolvido por:\nChelsea Duarte; Diogo Ribeiro; e Rodrigo Reis\n" +
                "2021100010; 2022136604; 2022137090");
        developedByLabel.setTextFill(Color.WHITE);
        developedByLabel.setFont(new Font(14));
        developedByLabel.setAlignment(Pos.CENTER);

        // Press any key to start
        Text pressAnyKeyText = new Text("Press any key to start");
        pressAnyKeyText.setFill(Color.WHITE);
        pressAnyKeyText.setFont(new Font(20));

        VBox vbox = new VBox(20, instituteLabel, titleText, developedByLabel, pressAnyKeyText);
        vbox.setAlignment(Pos.CENTER);

        root.getChildren().add(vbox);
        StackPane.setAlignment(vbox, Pos.CENTER);

        scene = new Scene(root, 800, 600);
    }

    private void registerHandlers() {
        scene.setOnKeyPressed(event -> {
            if (event.getCode() != KeyCode.UNDEFINED) {
                MainMenuUI mainMenu = new MainMenuUI(primaryStage);
                primaryStage.setScene(mainMenu.getScene());
            }
        });
    }

    public Scene getScene() {
        return scene;
    }
}
