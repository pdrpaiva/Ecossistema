package pt.isec.pa.javalife.ui.gui.scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pt.isec.pa.javalife.model.data.ecosystem.EcossistemaManager;
import pt.isec.pa.javalife.model.data.elements.*;

import java.io.File;

public class EcosystemUI {
    private Scene scene;
    private Stage primaryStage;
    private EcossistemaManager ecossistemaManager;
    private Pane ecosystemPane;
    private Button btnPlayPause, btnAddElement, btnCreateEcosystem, btnImport, btnExport, btnDelElement;
    private ComboBox<String> comboBox;
    private TextField txtX, txtY, txtId, txtType, txtEsq, txtDir, txtCima, txtBaixo;
    private Slider strenghtSlider;
    private int currentElementIDSelected = -1;

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

        // Botão Play/Pause
        btnPlayPause = new Button("Pause");
        toolbar.getChildren().addAll(btnPlayPause);
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

        comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Fauna", "Flora", "Inanimado");
        comboBox.setValue("Fauna");

        btnAddElement = new Button("Adicionar Elemento");
        btnCreateEcosystem = new Button("Criar Ecossistema");
        btnImport = new Button("Importar");
        btnExport = new Button("Exportar");
        btnDelElement = new Button("Deletar Elemento");

        txtX = new TextField();
        txtY = new TextField();
        txtId = new TextField();
        txtType = new TextField();
        txtEsq = new TextField();
        txtDir = new TextField();
        txtCima = new TextField();
        txtBaixo = new TextField();
        strenghtSlider = new Slider();

        sidebar.getChildren().addAll(comboBox, btnAddElement, btnCreateEcosystem, btnImport, btnExport, btnDelElement);
        root.setRight(sidebar);

        scene = new Scene(root, 800, 600);
    }

    private void registerHandlers() {
        // Handler para Play/Pause
        btnPlayPause.setOnAction(event -> {
            if (ecossistemaManager.isRunning()) {
                ecossistemaManager.pauseGame();
                btnPlayPause.setText("Play");
            } else {
                ecossistemaManager.resumeGame();
                btnPlayPause.setText("Pause");
            }
        });

        // Handler para adicionar elementos
        btnAddElement.setOnAction(event -> {
            String selectedType = comboBox.getSelectionModel().getSelectedItem();
            if (selectedType != null) {
                switch (selectedType) {
                    case "Fauna":
                        ecossistemaManager.addElementToRandomFreePosition(Elemento.FAUNA);
                        break;
                    case "Flora":
                        ecossistemaManager.addElementToRandomFreePosition(Elemento.FLORA);
                        break;
                    case "Inanimado":
                        ecossistemaManager.addElementToRandomFreePosition(Elemento.INANIMADO);
                        break;
                }
                updateEcosystemDisplay();
            }
        });

        /*// Handler para importar elementos
        btnImport.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                if (ecossistemaManager.carregarDeArquivo(selectedFile.getAbsolutePath())) {
                    updateEcosystemDisplay();
                    showAlert(Alert.AlertType.INFORMATION, "Sucesso", "O arquivo foi importado com sucesso.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erro", "Ocorreu um erro ao importar o arquivo.");
                }
            }
        });
*/
        /*// Handler para exportar elementos
        btnExport.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Salvar Arquivo");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Arquivos de simulação", "*.csv")
            );

            File file = fileChooser.showSaveDialog(primaryStage);
            if (file != null) {
                String filePath = file.getAbsolutePath();
                if (ecossistemaManager.salvarParaArquivo(filePath)) {
                    showAlert(Alert.AlertType.INFORMATION, "Sucesso", "O arquivo foi salvo com sucesso.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erro", "Ocorreu um erro ao salvar o arquivo.");
                }
            }
        });
*/
        // Handler para criar novo ecossistema
        btnCreateEcosystem.setOnAction(event -> {
            ecossistemaManager.limparElementos();
            CreateEcosystemUI createEcosystemUI = new CreateEcosystemUI(primaryStage, ecossistemaManager);
            primaryStage.setScene(createEcosystemUI.getScene());
        });

        // Handler para clicar no canvas do ecossistema
        ecosystemPane.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                double mouseX = event.getX();
                double mouseY = event.getY();

                for (IElemento elemento : ecossistemaManager.obterElementos()) {
                    if (mouseX >= elemento.getArea().esquerda() && mouseX <= elemento.getArea().direita() &&
                            mouseY >= elemento.getArea().cima() && mouseY <= elemento.getArea().baixo()) {

                        currentElementIDSelected = elemento.getId();
                        showInspectTab();

                        updateSidebar(elemento);

                        break;
                    }
                }
            }
        });

        // Handler para atualizar posição X do elemento
        txtX.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!ecossistemaManager.isPaused()) return;
            IElemento elemento = ecossistemaManager.buscarElemento(currentElementIDSelected);
            if (elemento == null) return;
            elemento.setPosicaoX(Integer.parseInt(newValue));
        });

        // Handler para atualizar posição Y do elemento
        txtY.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!ecossistemaManager.isPaused()) return;
            IElemento elemento = ecossistemaManager.buscarElemento(currentElementIDSelected);
            if (elemento == null) return;
            elemento.setPosicaoY(Integer.parseInt(newValue));
        });

        // Handler para atualizar força do elemento
        strenghtSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (!ecossistemaManager.isPaused()) return;
            IElemento elemento = ecossistemaManager.buscarElemento(currentElementIDSelected);
            if (elemento == null) return;
            if (elemento instanceof Fauna) {
                ((Fauna) elemento).setForca(newValue.doubleValue());
            } else if (elemento instanceof Flora) {
                ((Flora) elemento).setForca(newValue.doubleValue());
            }
        });

        // Handler para deletar elemento
        btnDelElement.setOnAction(event -> {
            IElemento elemento = ecossistemaManager.buscarElemento(currentElementIDSelected);
            if (elemento == null) return;
            ecossistemaManager.removerElemento(elemento.getId());
            updateEcosystemDisplay();
        });
    }

    private void updateEcosystemDisplay() {
        ecosystemPane.getChildren().clear();

        for (IElemento elemento : ecossistemaManager.obterElementos()) {
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

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void updateSidebar(IElemento elemento) {
        // Atualize os campos da sidebar com as informações do elemento
        txtId.setText(String.valueOf(elemento.getId()));
        txtType.setText(elemento.getClass().getSimpleName());
        txtX.setText(String.valueOf(elemento.getArea().esquerda()));
        txtY.setText(String.valueOf(elemento.getArea().cima()));
        txtEsq.setText(String.valueOf(elemento.getArea().esquerda()));
        txtDir.setText(String.valueOf(elemento.getArea().direita()));
        txtCima.setText(String.valueOf(elemento.getArea().cima()));
        txtBaixo.setText(String.valueOf(elemento.getArea().baixo()));

        if (elemento instanceof Fauna) {
            strenghtSlider.setValue(((Fauna) elemento).getForca());
        } else if (elemento instanceof Flora) {
            strenghtSlider.setValue(((Flora) elemento).getForca());
        }
    }

    private void showInspectTab() {
        // Lógica para exibir a aba de inspeção
    }

    public Scene getScene() {
        return scene;
    }
}
