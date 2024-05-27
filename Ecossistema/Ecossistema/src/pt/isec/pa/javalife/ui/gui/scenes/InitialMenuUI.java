package pt.isec.pa.javalife.ui.gui.scenes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
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
        root.setStyle("-fx-background-color: #01270E;");

        // Adicionando uma imagem
        ImageView imageView = new ImageView(new Image(getClass().getResource("/pt/isec/pa/javalife/ui/gui/resources/images/logo1.png").toExternalForm()));
        // Desativar ajuste automático da imagem
        imageView.setPreserveRatio(true);
        // Vincular a largura da imagem à largura da cena
        imageView.fitWidthProperty().bind(primaryStage.widthProperty());
        // Vincular a altura da imagem à altura da cena
        imageView.fitHeightProperty().bind(primaryStage.heightProperty());

        // Adicionando imageView ao root
        root.getChildren().add(imageView);
        StackPane.setAlignment(imageView, Pos.CENTER);

//        // Labels para as linhas de texto
//        Label isecLabel = new Label("Instituto Superior de Engenharia de Coimbra\n" +
//                                       "     Licenciatura em Engenharia Informática\n" +
//                                       "        Programação Avançada - 2023/2024");
//
//        // Configurando o estilo e a posição das labels
//        isecLabel.setTextFill(Color.WHITE);
//        isecLabel.setFont(new Font(20));
//
//        // Centralizando as labels horizontalmente
//        StackPane.setAlignment(isecLabel, Pos.TOP_CENTER);
//
//        // Adicionando as labels ao root
//        root.getChildren().addAll(isecLabel);
//
//        // Trabalho realizado por
//        Label nomesLabel = new Label("                 Realizado por:\n" +
//                                        "Manuel Vicente   -   2020151796 \n" +
//                "Pedro Paiva         -   2021134625 \n" +
//                "Tomás Ferreira    -   2021130424 ");
//        nomesLabel.setTextFill(Color.WHITE);
//        nomesLabel.setFont(new Font(16));
//        StackPane.setAlignment(nomesLabel, Pos.BOTTOM_CENTER);
//
//        // Adicionando nomesLabel ao root
//        root.getChildren().add(nomesLabel);

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