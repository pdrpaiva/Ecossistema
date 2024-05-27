package pt.isec.pa.javalife.ui.gui.scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreateEcosystemUI {
    private Scene scene;
    private Stage primaryStage;

    private Button createButton;

    public CreateEcosystemUI(Stage primaryStage) {
        this.primaryStage = primaryStage;
        createViews();
        registerHandlers();
    }

    private void createViews() {
        HBox root = new HBox(20);
        root.setAlignment(Pos.CENTER);
        root.getStyleClass().add("CreateEcosystemUI");

        // Adicionando uma imagem
        ImageView imageView = new ImageView(new Image(getClass().getResource("/pt/isec/pa/javalife/ui/gui/resources/images/teste.png").toExternalForm()));
        imageView.setFitWidth(350);
        imageView.setPreserveRatio(true);

        // Criando uma VBox para os campos de entrada e botões
        VBox formContainer = new VBox(20);
        formContainer.setAlignment(Pos.CENTER);
        formContainer.setPadding(new Insets(20));
        formContainer.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-background-radius: 10;");

        // Título
        Label title = new Label("Criação do Ecossistema");
        title.getStyleClass().add("title");

        // Campos de entrada
        VBox fieldsContainer = new VBox(15);
        fieldsContainer.setAlignment(Pos.CENTER_LEFT);
        fieldsContainer.getChildren().addAll(
                createLabeledField("Nome", ""),
                createLabeledField("Altura", "300"),
                createLabeledField("Comprimento", "300"),
                createLabeledField("Quantidade Fauna", "10"),
                createLabeledField("Quantidade Flora", "10"),
                createLabeledField("Quantidade Inanimados", "10"),
                createTimeUnitField()
        );

        // Botão Criar
        createButton = new Button("Criar");
        createButton.getStyleClass().add("create-button");

        formContainer.getChildren().addAll(title, fieldsContainer, createButton);

        root.getChildren().addAll(imageView, formContainer);

        scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/pt/isec/pa/javalife/ui/gui/resources/css/styles.css").toExternalForm());
    }

    private HBox createLabeledField(String labelText, String textFieldValue) {
        HBox box = new HBox(10);
        box.setAlignment(Pos.CENTER_LEFT);
        Label label = new Label(labelText);
        label.getStyleClass().add("label");
        TextField textField = new TextField(textFieldValue);
        textField.getStyleClass().add("text-field");
        box.getChildren().addAll(label, textField);
        return box;
    }

    private HBox createTimeUnitField() {
        HBox box = new HBox(10);
        box.setAlignment(Pos.CENTER_LEFT);
        Label label = new Label("Unidade de tempo");
        label.getStyleClass().add("label");
        Slider slider = new Slider(100, 1000, 100);
        slider.getStyleClass().add("slider");
        box.getChildren().addAll(label, slider);
        return box;
    }

    private void registerHandlers() {
        createButton.setOnAction(event -> {
            EcosystemUI ecosystemUI = new EcosystemUI(primaryStage);
            primaryStage.setScene(ecosystemUI.getScene());
        });
    }

    public Scene getScene() {
        return scene;
    }
}
