package pt.isec.pa.javalife.ui.gui.panes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import pt.isec.pa.javalife.model.facade.JavaLifeFacade;

public class InitialMenuUI extends Scene {

    private final JavaLifeFacade facade;
    private final Stage primaryStage;
    private VBox root;

    public InitialMenuUI(JavaLifeFacade facade, Stage primaryStage){
        super(new VBox());
        this.facade = facade;
        this.primaryStage = primaryStage;

        createView();
        registerHandlers();
    }


    private void createView() {
        getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        root = (VBox) this.getRoot();

        root.getStyleClass().add("primary-background");
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);

        Label lblCourse = new Label("Instituto Superior de Engenharia de Coimbra\nLicenciatura em Engenharia Informática\nProgramação Avançada - 2023/2024");
        lblCourse.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        lblCourse.setStyle("-fx-text-alignment: center; -fx-text-fill: white;");

        Label lblTitle = new Label("JavaLife");
        lblTitle.setFont(Font.font("Arial", FontWeight.BOLD, 60));
        lblTitle.setStyle("-fx-text-fill: white;");

        Label lblPrompt = new Label("Pressione qualquer tecla para começar");
        lblPrompt.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
        lblPrompt.setStyle("-fx-text-fill: white;");

        root.getChildren().addAll(lblCourse, lblTitle, lblPrompt);

        primaryStage.setWidth(800);
        primaryStage.setHeight(600);

    }


    private void registerHandlers(){
        this.setOnKeyPressed(this::handleKeyPress);
    }


    private void handleKeyPress(KeyEvent event) {
        MainMenuUI mainMenu = new MainMenuUI(facade, primaryStage);
        Scene scene = new Scene(mainMenu, 800, 600);
        primaryStage.setScene(scene);
    }

}
