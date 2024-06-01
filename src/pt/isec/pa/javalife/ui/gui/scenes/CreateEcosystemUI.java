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
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pt.isec.pa.javalife.model.data.area.Area;
import pt.isec.pa.javalife.model.data.elements.Elemento;
import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.data.elements.Flora;
import pt.isec.pa.javalife.model.data.elements.Inanimado;
import pt.isec.pa.javalife.model.data.ecosystem.EcossistemaManager;

import java.util.Random;

public class CreateEcosystemUI {
    private Scene scene;
    private Stage primaryStage;
    private EcossistemaManager ecossistemaManager;
    private Button createButton;
    private TextField txtNome, txtAltura, txtComprimento, txtFauna, txtFlora, txtInanimados;
    private Slider sliderTempo;

    public CreateEcosystemUI(Stage primaryStage, EcossistemaManager ecossistemaManager) {
        this.primaryStage = primaryStage;
        this.ecossistemaManager = ecossistemaManager;
        createViews();
        registerHandlers();
    }

    private void createViews() {
        HBox root = new HBox(20);
        root.setAlignment(Pos.CENTER);
        root.getStyleClass().add("CreateEcosystemUI");

        ImageView imageView = new ImageView(new Image(getClass().getResource("/pt/isec/pa/javalife/ui/gui/resources/images/teste.png").toExternalForm()));
        imageView.setFitWidth(350);
        imageView.setPreserveRatio(true);

        VBox formContainer = new VBox(20);
        formContainer.setAlignment(Pos.CENTER);
        formContainer.setPadding(new Insets(20));
        formContainer.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-background-radius: 10;");

        Label title = new Label("Criação do Ecossistema");
        title.getStyleClass().add("title");

        VBox fieldsContainer = new VBox(15);
        fieldsContainer.setAlignment(Pos.CENTER_LEFT);

        txtNome = new TextField();
        txtAltura = new TextField("800");
        txtComprimento = new TextField("800");
        txtFauna = new TextField("10");
        txtFlora = new TextField("10");
        txtInanimados = new TextField("10");
        sliderTempo = new Slider(100, 1000, 100);

        fieldsContainer.getChildren().addAll(
                createLabeledField("Nome", txtNome),
                createLabeledField("Altura", txtAltura),
                createLabeledField("Comprimento", txtComprimento),
                createLabeledField("Fauna", txtFauna),
                createLabeledField("Flora", txtFlora),
                createLabeledField("Inanimados", txtInanimados),
                createTimeUnitField() // Agora o rótulo com as unidades está abaixo do slider
        );

        createButton = new Button("Criar");
        createButton.getStyleClass().add("create-button");

        formContainer.getChildren().addAll(title, fieldsContainer, createButton);

        root.getChildren().addAll(imageView, formContainer);

        scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/pt/isec/pa/javalife/ui/gui/resources/css/CreateEcosystem.css").toExternalForm());
    }

    private HBox createLabeledField(String labelText, TextField textField) {
        HBox box = new HBox(10);
        box.setAlignment(Pos.CENTER_LEFT);
        Label label = new Label(labelText);
        label.getStyleClass().add("label");
        label.setMinWidth(120);
        textField.getStyleClass().add("text-field");
        textField.setPrefWidth(200);
        HBox.setHgrow(textField, Priority.ALWAYS);
        box.getChildren().addAll(label, textField);
        return box;
    }

//    private HBox createTimeUnitField() {
//        HBox box = new HBox(10);
//        box.setAlignment(Pos.CENTER_LEFT);
//        Label label = new Label("Tempo");
//        label.getStyleClass().add("label");
//        label.setMinWidth(120);
//        sliderTempo.getStyleClass().add("slider");
//        sliderTempo.setPrefWidth(200);
//        HBox.setHgrow(sliderTempo, Priority.ALWAYS);
//        box.getChildren().addAll(label, sliderTempo);
//        return box;
//    }
private VBox createTimeUnitField() {
    VBox box = new VBox(5);
    box.setAlignment(Pos.CENTER);

    Label label = new Label("");
    label.getStyleClass().add("label");
    label.setMinWidth(120);

    sliderTempo = new Slider(1, 1000, 100);
    sliderTempo.getStyleClass().add("slider");
    sliderTempo.setPrefWidth(200);

    // Configurar os Tick Marks
    sliderTempo.setShowTickMarks(true);
    sliderTempo.setShowTickLabels(true);
    sliderTempo.setMajorTickUnit(100);
    sliderTempo.setMinorTickCount(4);
    sliderTempo.setBlockIncrement(5);

    Label lblSliderValue = new Label(String.format("%.0f Unidades de Tempo", sliderTempo.getValue()));
    lblSliderValue.getStyleClass().add("slider-value");
    lblSliderValue.setAlignment(Pos.CENTER); // Centraliza o texto dentro da Label

    sliderTempo.valueProperty().addListener((obs, oldval, newVal) ->
            lblSliderValue.setText(String.format("%.0f Unidades de Tempo", newVal.doubleValue()))
    );

    box.getChildren().addAll(sliderTempo, lblSliderValue);
    return box;
}

    private void registerHandlers() {
        createButton.setOnAction(event -> {
            System.out.println("Botão 'Criar' foi clicado");
            int altura = Integer.parseInt(txtAltura.getText());
            int comprimento = Integer.parseInt(txtComprimento.getText());
            int faunaCount = Integer.parseInt(txtFauna.getText());
            int floraCount = Integer.parseInt(txtFlora.getText());
            int inanimadosCount = Integer.parseInt(txtInanimados.getText());
            int tempo = (int) sliderTempo.getValue();

            ecossistemaManager.definirIntervaloJogo(tempo);

            // Definir tamanho do ecossistema
            ecossistemaManager.getEcossistema().definirUnidadesY(altura);
            ecossistemaManager.getEcossistema().definirUnidadesX(comprimento);
            ecossistemaManager.criarCerca();

            // Adicionar elementos ao ecossistema
            adicionarElementos(ecossistemaManager, Elemento.INANIMADO, inanimadosCount);
            adicionarElementos(ecossistemaManager, Elemento.FAUNA, faunaCount);
            adicionarElementos(ecossistemaManager, Elemento.FLORA, floraCount);

            // Navegar para a próxima tela
            EcosystemUI ecosystemUI = new EcosystemUI(primaryStage, ecossistemaManager);
            primaryStage.setScene(ecosystemUI.getScene());
            ecossistemaManager.retomarJogo();
        });
    }

    private void adicionarElementos(EcossistemaManager manager, Elemento tipo, int quantidade) {
        for (int i = 0; i < quantidade; i++) {
            manager.adicionarElementoAleatoriamente(tipo);
        }
    }

    public Scene getScene() {
        return scene;
    }
}
