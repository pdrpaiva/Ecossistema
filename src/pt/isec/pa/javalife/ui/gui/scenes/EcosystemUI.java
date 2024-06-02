package pt.isec.pa.javalife.ui.gui.scenes;

import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pt.isec.pa.javalife.model.data.area.Area;
import pt.isec.pa.javalife.model.data.ecosystem.EcossistemaManager;
import pt.isec.pa.javalife.model.data.elements.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class EcosystemUI {
    private Scene scene;
    private Stage primaryStage;
    private EcossistemaManager ecossistemaManager;
    private Canvas canvas;
    private GraphicsContext gc;
    private Button btnPlayPause, btnFauna, btnFlora, btnInanimado, btnVoltar, btnUndo, btnRedo, btnSalvarSnapshot, btnRestaurarSnapshot;
    private TextField txtX, txtY, txtId, txtType, txtEsq, txtDir, txtCima, txtBaixo, txtEnergia, txtEstado;
    private Slider strenghtSlider;
    private int currentElementIDSelected = -1;
    private Map<Integer, Area> previousPositions;
    private Button btnExport, btnImport;

    private Button btnAplicarSol, btnAplicarHerbicida, btnInjetarForca, btnApagar;

    public EcosystemUI(Stage primaryStage, EcossistemaManager ecossistemaManager) {
        this.primaryStage = primaryStage;
        this.ecossistemaManager = ecossistemaManager;
        this.previousPositions = new HashMap<>();
        createViews();
        registerHandlers();
        updateEcosystemDisplay();

        // Adicionar um listener para mudanças no ecossistema
        ecossistemaManager.getEcossistema().addPropertyChangeListener(evt -> {
            if ("evolucao".equals(evt.getPropertyName()) || "elemento_adicionado".equals(evt.getPropertyName()) || "elemento_removido".equals(evt.getPropertyName()) || "ecossistema_atualizado".equals(evt.getPropertyName())) {
                Platform.runLater(this::updateEcosystemDisplay);
            }
        });
    }

    private void createViews() {
        BorderPane root = new BorderPane();
        root.getStyleClass().add("root");

        // Remove o Top menu bar e a ToolBar
        // ToolBar toolBar = new ToolBar(); // Remover a barra de menu

        // Botões que estavam na ToolBar
        btnUndo = new Button();
        btnUndo.getStyleClass().addAll("play-pause", "transparent-button");
        setButtonImage(btnUndo, "undo.png");
        Tooltip.install(btnUndo, new Tooltip("Desfazer"));

        btnRedo = new Button();
        btnRedo.getStyleClass().addAll("play-pause", "transparent-button");
        setButtonImage(btnRedo, "redo.png");
        Tooltip.install(btnRedo, new Tooltip("Refazer"));

        btnExport = new Button();
        btnExport.getStyleClass().addAll("play-pause", "transparent-button");
        setButtonImage(btnExport, "export.png");
        Tooltip.install(btnExport, new Tooltip("Exportar"));

        btnImport = new Button();
        btnImport.getStyleClass().addAll("play-pause", "transparent-button");
        setButtonImage(btnImport, "import.png");
        Tooltip.install(btnImport, new Tooltip("Importar"));

        btnPlayPause = new Button();
        btnPlayPause.getStyleClass().addAll("play-pause", "transparent-button");
        setButtonImage(btnPlayPause, "pause.png"); // Define a imagem inicial como "play"
        Tooltip.install(btnPlayPause, new Tooltip("Play/Pause"));

        btnVoltar = new Button();
        btnVoltar.getStyleClass().add("transparent-button");
        setButtonImage(btnVoltar, "back.png");
        Tooltip.install(btnVoltar, new Tooltip("Voltar"));

        // Área central do ecossistema com Canvas
        canvas = new Canvas(ecossistemaManager.getLargura(), ecossistemaManager.getAltura());
        gc = canvas.getGraphicsContext2D();
        Pane canvasPane = new Pane(canvas);
        canvasPane.getStyleClass().add("canvas-pane");
        root.setCenter(canvasPane);

        // Right sidebar for controls
        VBox sidebar = new VBox(20);
        sidebar.setPadding(new Insets(10));
        sidebar.getStyleClass().add("sidebar");
        sidebar.setAlignment(Pos.TOP_CENTER);

        // Adicionar botões de Undo, Redo, Export e Import na sidebar
        //sidebar.getChildren().addAll(btnUndo, btnRedo, btnExport, btnImport);

        // HBox para Play/Pause e Voltar lado a lado
        HBox hboxPlayPauseVoltar = new HBox(10);
        hboxPlayPauseVoltar.setAlignment(Pos.TOP_CENTER);

        hboxPlayPauseVoltar.getChildren().addAll(btnUndo, btnRedo, btnExport, btnImport,btnPlayPause, btnVoltar);
        sidebar.getChildren().add(hboxPlayPauseVoltar);

        // Separador
        Separator separator2 = new Separator();
        sidebar.getChildren().add(separator2);

        // Botões de adicionar elementos
        Label addLabel = new Label("ADICIONAR");
        addLabel.getStyleClass().add("sidebar-title");
        sidebar.getChildren().add(addLabel);

        btnFauna = new Button("Fauna");
        btnFlora = new Button("Flora");
        btnInanimado = new Button("Inanimado");

        btnFauna.getStyleClass().add("sidebar-button");
        Tooltip.install(btnFauna, new Tooltip("Adicionar Fauna"));
        btnFlora.getStyleClass().add("sidebar-button");
        Tooltip.install(btnFlora, new Tooltip("Adicionar Flora"));
        btnInanimado.getStyleClass().add("sidebar-button");
        Tooltip.install(btnInanimado, new Tooltip("Adicionar Inanimado"));

        GridPane addGrid = new GridPane();
        addGrid.setHgap(10);
        addGrid.setVgap(10);

        // Add buttons to the grid
        addGrid.add(btnFauna, 0, 0);
        addGrid.add(btnFlora, 1, 0);

        // Add the Inanimado button in the center
        GridPane.setColumnSpan(btnInanimado, 2);
        GridPane.setHalignment(btnInanimado, HPos.CENTER);
        addGrid.add(btnInanimado, 0, 1);

        sidebar.getChildren().add(addGrid);

        // Botões de interação
        Label interactionLabel = new Label("INTERAÇÃO");
        interactionLabel.getStyleClass().add("sidebar-title");
        sidebar.getChildren().add(interactionLabel);

        btnAplicarSol = new Button("Sol");
        Tooltip.install(btnAplicarSol, new Tooltip("Aplicar Sol"));
        btnAplicarHerbicida = new Button("Herbicida");
        Tooltip.install(btnAplicarHerbicida, new Tooltip("Aplicar Herbicida"));
        btnInjetarForca = new Button("Força");
        Tooltip.install(btnInjetarForca, new Tooltip("Injetar Força"));
        btnApagar = new Button("Apagar");
        Tooltip.install(btnApagar, new Tooltip("Apagar"));

        btnAplicarSol.getStyleClass().add("sidebar-button");
        btnAplicarHerbicida.getStyleClass().add("sidebar-button");
        btnInjetarForca.getStyleClass().add("sidebar-button");
        btnApagar.getStyleClass().add("sidebar-button");

        GridPane interactionGrid = new GridPane();
        interactionGrid.setHgap(10);
        interactionGrid.setVgap(10);
        interactionGrid.add(btnAplicarSol, 0, 0);
        interactionGrid.add(btnAplicarHerbicida, 1, 0);
        interactionGrid.add(btnInjetarForca, 0, 1);
        interactionGrid.add(btnApagar, 1, 1);

        sidebar.getChildren().add(interactionGrid);

        // Botões de snapshot
        Label snapshotLabel = new Label("SNAPSHOT");
        snapshotLabel.getStyleClass().add("sidebar-title");
        sidebar.getChildren().add(snapshotLabel);

        btnSalvarSnapshot = new Button("Guardar");
        Tooltip.install(btnSalvarSnapshot, new Tooltip("Guardar Snapshot"));
        btnRestaurarSnapshot = new Button("Restaurar");
        Tooltip.install(btnRestaurarSnapshot, new Tooltip("Restaurar Snapshot"));

        btnSalvarSnapshot.getStyleClass().add("sidebar-button");
        btnRestaurarSnapshot.getStyleClass().add("sidebar-button");

        GridPane snapshotGrid = new GridPane();
        snapshotGrid.setHgap(10);
        snapshotGrid.setVgap(10);
        snapshotGrid.add(btnSalvarSnapshot, 0, 0);
        snapshotGrid.add(btnRestaurarSnapshot, 1, 0);

        sidebar.getChildren().add(snapshotGrid);

        // TextFields para informação do elemento
        txtX = new TextField();
        txtY = new TextField();
        txtId = new TextField();
        txtType = new TextField();
        txtEsq = new TextField();
        txtDir = new TextField();
        txtCima = new TextField();
        txtBaixo = new TextField();
        txtEnergia = new TextField();
        txtEstado = new TextField();
        txtEnergia.setEditable(false);
        txtEnergia.getStyleClass().add("text-field");
        txtEstado.setEditable(false);
        txtEstado.getStyleClass().add("text-field");

        // Separador
        Separator separator3 = new Separator();
        sidebar.getChildren().add(separator3);

        // Single label for all information
        Label infoLabel = new Label();
        infoLabel.getStyleClass().add("info-label");
        sidebar.getChildren().add(infoLabel);

        // Update the infoLabel whenever necessário
        txtId.textProperty().addListener((observable, oldValue, newValue) -> {
            updateInfoLabel(infoLabel);
        });
        txtEnergia.textProperty().addListener((observable, oldValue, newValue) -> {
            updateInfoLabel(infoLabel);
        });
        txtEstado.textProperty().addListener((observable, oldValue, newValue) -> {
            updateInfoLabel(infoLabel);
        });

        root.setRight(sidebar);

        scene = new Scene(root, 800, 600); // Valor inicial, pode ser ajustado depois
        String css = getClass().getResource("/pt/isec/pa/javalife/ui/gui/resources/css/Ecossystem.css").toExternalForm();
        if (css == null) {
            System.out.println("CSS file not found. Please check the path.");
        } else {
            scene.getStylesheets().add(css);
        }

        // Ajustar o tamanho da janela conforme o ecossistema
        ajustarTamanhoJanela(ecossistemaManager.getLargura(), ecossistemaManager.getAltura());
    }

    private void registerHandlers() {
        // Handler para Play/Pause
        btnPlayPause.setOnAction(event -> {
            if (ecossistemaManager.isRunning()) {
                ecossistemaManager.pausarJogo();
                setButtonImage(btnPlayPause, "play.png"); // Caminho da imagem de play
            } else {
                ecossistemaManager.retomarJogo();
                setButtonImage(btnPlayPause, "pause.png"); // Caminho da imagem de pause
            }
        });

        // Handler para adicionar elementos
        btnFauna.setOnAction(event -> {
            ecossistemaManager.adicionarElementoAleatoriamente2(Elemento.FAUNA);
            Platform.runLater(this::updateEcosystemDisplay);
        });

        btnFlora.setOnAction(event -> {
            ecossistemaManager.adicionarElementoAleatoriamente2(Elemento.FLORA);
            Platform.runLater(this::updateEcosystemDisplay);
        });

        btnInanimado.setOnAction(event -> {
            ecossistemaManager.adicionarElementoAleatoriamente2(Elemento.INANIMADO);
            Platform.runLater(this::updateEcosystemDisplay);
        });



        btnVoltar.setOnAction(event -> {
            ecossistemaManager.limparElementos();
            // Assumindo que você tem uma forma de obter a nova largura e altura desejadas
            int novaLargura = 600; // Substitua com o valor correto
            int novaAltura = 580; // Substitua com o valor correto
            ecossistemaManager.setLargura(novaLargura);
            ecossistemaManager.setAltura(novaAltura);
            ajustarTamanhoJanela(novaLargura, novaAltura);

            CreateEcosystemUI createEcosystemUI = new CreateEcosystemUI(primaryStage, ecossistemaManager);
            primaryStage.setScene(createEcosystemUI.getScene());
        });

        // Handler para clicar no canvas do ecossistema
        canvas.setOnMouseClicked(event -> {
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

        btnUndo.setOnAction(event -> {
            ecossistemaManager.undo();
            Platform.runLater(this::updateEcosystemDisplay);
        });

        btnRedo.setOnAction(event -> {
            ecossistemaManager.redo();
            Platform.runLater(this::updateEcosystemDisplay);
        });

        // Handler para aplicar Sol
        btnAplicarSol.setOnAction(event -> {
            ecossistemaManager.applySun(); // Define a duração do efeito do Sol
            Platform.runLater(this::updateEcosystemDisplay);
        });

        // Handler para aplicar Herbicida
        btnAplicarHerbicida.setOnAction(event -> {
            IElemento elemento = ecossistemaManager.buscarElemento(currentElementIDSelected);
            if (elemento instanceof Flora) {
                ecossistemaManager.applyHerbicide((Flora) elemento);
                Platform.runLater(this::updateEcosystemDisplay);
            } else {
                showAlert(Alert.AlertType.WARNING, "Aviso", "Selecione um elemento do tipo Flora para aplicar o herbicida.");
            }
        });

        // Handler para injetar força
        btnInjetarForca.setOnAction(event -> {
            IElemento elemento = ecossistemaManager.buscarElemento(currentElementIDSelected);
            if (elemento instanceof Fauna) {
                ecossistemaManager.injectForce((Fauna) elemento, 50); // Define a quantidade de força a ser injetada
                Platform.runLater(this::updateEcosystemDisplay);
            } else {
                showAlert(Alert.AlertType.WARNING, "Aviso", "Selecione um elemento do tipo Fauna para injetar força.");
            }
        });

        // Handler para exportar elementos para CSV
        btnExport.setOnAction(event -> {
            if (!ecossistemaManager.isPaused()) {
                showAlert(Alert.AlertType.WARNING, "Aviso", "Pause a simulação antes de exportar.");
                return;
            }
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar Ficheiro");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
            File file = fileChooser.showSaveDialog(primaryStage);
            if (file != null) {
                try {
                    ecossistemaManager.exportarElementosParaCSV(file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btnImport.setOnAction(event -> {
            if (!ecossistemaManager.isPaused()) {
                showAlert(Alert.AlertType.WARNING, "Aviso", "Pause a simulação antes de importar.");
                return;
            }
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Abrir Arquivo");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                try {
                    ecossistemaManager.importarElementosDeCSV(file);
                    showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Elementos importados com sucesso.");
                } catch (IOException e) {
                    showAlert(Alert.AlertType.ERROR, "Erro", "Erro ao importar elementos: " + e.getMessage());
                }
            }
        });

        // Handler para atualizar posição X do elemento
        txtX.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!ecossistemaManager.isPaused()) return;
            IElemento elemento = ecossistemaManager.buscarElemento(currentElementIDSelected);
            if (elemento == null) return;
            elemento.setPosicaoX((int) Double.parseDouble(newValue));
        });

        // Handler para atualizar posição Y do elemento
        txtY.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!ecossistemaManager.isPaused()) return;
            IElemento elemento = ecossistemaManager.buscarElemento(currentElementIDSelected);
            if (elemento == null) return;
            elemento.setPosicaoY((int) Double.parseDouble(newValue));
        });

        // Handler para apagar elementos
//        btnApagar.setOnAction(event -> {
//            IElemento elemento = ecossistemaManager.buscarElemento(currentElementIDSelected);
//            if (elemento == null) return;
//
//            ecossistemaManager.removerElemento(elemento);
//            Platform.runLater(this::updateEcosystemDisplay);
//
//            // Limpar os campos de texto
//            txtId.setText("");
//            txtEnergia.setText("");
//            txtEstado.setText("");
//
//            // Resetar a seleção de ID do elemento atual
//            currentElementIDSelected = -1;
//        });

        btnApagar.setOnAction(event -> {
            IElemento elemento = ecossistemaManager.buscarElemento(currentElementIDSelected);
            if (elemento == null) return;

            ecossistemaManager.removerElemento(elemento.getId());
            Platform.runLater(this::updateEcosystemDisplay);
        });

        // Handler para salvar snapshot
        btnSalvarSnapshot.setOnAction(event -> {
            try {
                ecossistemaManager.salvarSnapshot();
                showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Snapshot salvo com sucesso.");
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Erro", "Erro ao salvar snapshot: " + e.getMessage());
            }
        });

        // Handler para restaurar snapshot
        btnRestaurarSnapshot.setOnAction(event -> {
            try {
                ecossistemaManager.restaurarSnapshot();
                showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Snapshot restaurado com sucesso.");
                Platform.runLater(this::updateEcosystemDisplay);
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Erro", "Erro ao restaurar snapshot: " + e.getMessage());
            }
        });
    }


    private void updateEcosystemDisplay() {
        // Limpa o fundo do canvas
        gc.setFill(Color.web("#49361F"));
        gc.fillRect(0, 0, ecossistemaManager.getLargura(), ecossistemaManager.getAltura());

        Collection<IElemento> elementos = ecossistemaManager.obterElementos();
        //E Se eu Criar uma lista separada para as entradas a serem limpas
        List<Map.Entry<Integer, Area>> entriesToClear = new ArrayList<>(previousPositions.entrySet());

        // Limpar a posição anterior de todos os elementos de Fauna
        for (Map.Entry<Integer, Area> entry : entriesToClear) {
            Area previousArea = entry.getValue();
            gc.clearRect(previousArea.esquerda(), previousArea.cima(), previousArea.direita() - previousArea.esquerda(), previousArea.baixo() - previousArea.cima());
        }

        // Desenhar todos os elementos
        for (IElemento elemento : elementos) {
            Area area = elemento.getArea();
            double width = area.direita() - area.esquerda();
            double height = area.baixo() - area.cima();

            if (elemento instanceof Flora) {
                Flora fl = (Flora) elemento;
                double strength = fl.getForca();
                double maxStrength = 100.0; // Defina a força máxima esperada
                double opacity = Math.max(0, Math.min(1, strength / maxStrength)); // Calcula a opacidade baseada na força
                Color floraColor = Color.GREENYELLOW.deriveColor(0, 1, 1, opacity);
                gc.setFill(floraColor);
                gc.fillRect(area.esquerda(), area.cima(), width, height);
            } else if (elemento instanceof Inanimado) {
                gc.setFill(Color.GRAY);
                gc.fillRect(area.esquerda(), area.cima(), width, height);
            } else if (elemento instanceof Fauna) {
                gc.setFill(Color.RED);
                gc.fillRect(area.esquerda(), area.cima(), width, height);
            } else {
                System.out.println("Elemento desconhecido: " + elemento.getClass().getName());
            }
        }

        // Desenhar a caixa de seleção ao redor do elemento inspecionado
        if (currentElementIDSelected != -1) {
            IElemento elemento = ecossistemaManager.buscarElemento(currentElementIDSelected);
            if (elemento != null) {
                Area area = elemento.getArea();
                gc.setStroke(Color.WHITE);
                gc.strokeRect(area.esquerda() - 2, area.cima() - 2, area.direita() - area.esquerda() + 4, area.baixo() - area.cima() + 4);
                updateSidebar(elemento);
            }
        }
    }

    private void updateSidebar(IElemento elemento) {
        txtX.setText(String.valueOf(elemento.getPositionX()));
        txtY.setText(String.valueOf(elemento.getPositionY()));
        txtId.setText(String.valueOf(elemento.getId()));
        txtType.setText(elemento.getClass().getSimpleName());

        txtEsq.setText(String.valueOf(elemento.getArea().esquerda()));
        txtDir.setText(String.valueOf(elemento.getArea().direita()));
        txtCima.setText(String.valueOf(elemento.getArea().cima()));
        txtBaixo.setText(String.valueOf(elemento.getArea().baixo()));

        if (elemento instanceof Fauna) {
            txtEnergia.setText(String.valueOf(((Fauna) elemento).getForca())); // Atualiza a energia da Fauna
            txtEstado.setText(((Fauna) elemento).getFaunaContext().getCurrentStateAsString());
        } else if (elemento instanceof Flora) {
            txtEnergia.setText(String.valueOf(((Flora) elemento).getForca())); // Atualiza a energia da Flora
            txtEstado.setText("");
        } else {
            txtEnergia.setText("");
            txtEstado.setText("");
        }
    }


    private void showInspectTab() {
        // Implementar a lógica para mostrar a aba de inspeção
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void ajustarTamanhoJanela(int largura, int altura) {
        primaryStage.setWidth(largura + 250); // Ajuste o valor adicional conforme necessário
        primaryStage.setHeight(altura + 40); // Ajuste o valor adicional conforme necessário
        canvas.setWidth(largura);
        canvas.setHeight(altura);
        updateEcosystemDisplay(); // Atualiza a exibição do ecossistema após o ajuste
    }

    private void setButtonImage(Button button, String imagePath) {
        Image image = new Image(getClass().getResourceAsStream("/pt/isec/pa/javalife/ui/gui/resources/images/" + imagePath));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(20); // Ajuste o tamanho conforme necessário
        imageView.setFitWidth(20);
        button.setGraphic(imageView);
    }

    private void updateInfoLabel(Label infoLabel) {
        String id = txtId.getText();
        String energia = txtEnergia.getText();
        String estado = txtEstado.getText();
        double energiaValue = 0.0;
        try {
            energiaValue = Double.parseDouble(energia);
        } catch (NumberFormatException e) {
        }
        String energiaFormatted = String.format("%.2f", energiaValue);

        infoLabel.setText(String.format("Id: %s | Força: %s | %s", id, energiaFormatted, estado));
    }

    public Scene getScene() {
        return scene;
    }

    private Separator createSeparator() {
        Separator separator = new Separator();
        separator.setOrientation(Orientation.VERTICAL);
        separator.getStyleClass().add("separator");
        return separator;
    }
}
