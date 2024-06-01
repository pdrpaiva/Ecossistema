package pt.isec.pa.javalife.ui.gui.scenes;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

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
        root.getStyleClass().add("InitialMenuUI");

        // Adicionando uma imagem
        ImageView imageView = new ImageView(new Image(getClass().getResource("/pt/isec/pa/javalife/ui/gui/resources/images/teste.png").toExternalForm()));
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(300);

        // Texto piscante
        Label blinkText = new Label("Press any key to start...");
        blinkText.getStyleClass().add("blink-text");
        blinkText.setFont(new Font(24));

        // Animação para o texto piscante
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.5), evt -> blinkText.setVisible(false)),
                new KeyFrame(Duration.seconds(1), evt -> blinkText.setVisible(true))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        // VBox para alinhar o logo e o texto
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(imageView, blinkText);

        // Adicionando vbox ao root
        root.getChildren().add(vbox);

        scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/pt/isec/pa/javalife/ui/gui/resources/css/InitialMenu.css").toExternalForm());
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
