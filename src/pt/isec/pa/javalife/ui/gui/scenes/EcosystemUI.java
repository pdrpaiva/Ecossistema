package pt.isec.pa.javalife.ui.gui.scenes;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pt.isec.pa.javalife.model.data.area.Area;
import pt.isec.pa.javalife.model.data.ecosystem.EcossistemaManager;
import pt.isec.pa.javalife.model.data.elements.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class EcosystemUI {
    private Scene scene;
    private Stage primaryStage;
    private EcossistemaManager ecossistemaManager;
    private Canvas canvas;
    private GraphicsContext gc;
    private Button btnPlayPause, btnAddElement, btnCreateEcosystem, btnImport, btnExport, btnDelElement;
    private ComboBox<String> comboBox;
    private TextField txtX, txtY, txtId, txtType, txtEsq, txtDir, txtCima, txtBaixo, txtEnergia;
    private Slider strenghtSlider;
    private int currentElementIDSelected = -1;
    private Map<Integer, Area> previousPositions;
    private MenuItem exportMenuItem;

    private Button btnAplicarSol, btnAplicarHerbicida, btnInjetarForca;

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
        root.setStyle("-fx-background-color: #00593c;");

        // Top menu bar
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        exportMenuItem = new MenuItem("Export");
        fileMenu.getItems().add(exportMenuItem);
        menuBar.getMenus().add(fileMenu);
        root.setTop(menuBar);

        // Top toolbar
        HBox toolbar = new HBox(10);
        toolbar.setPadding(new Insets(10));
        toolbar.setAlignment(Pos.CENTER_LEFT);
        toolbar.setStyle("-fx-background-color: #00593c;");

        // Botão Play/Pause
        btnPlayPause = new Button("Pause");
        toolbar.getChildren().addAll(btnPlayPause);
        root.setTop(toolbar);


        // Botões de eventos
        btnAplicarSol = new Button("Aplicar Sol");
        btnAplicarHerbicida = new Button("Aplicar Herbicida");
        btnInjetarForca = new Button("Injetar Força");
        toolbar.getChildren().addAll(btnAplicarSol, btnAplicarHerbicida, btnInjetarForca);
        root.setTop(toolbar);

        // Área central do ecossistema com Canvas
        canvas = new Canvas(ecossistemaManager.getLargura(), ecossistemaManager.getAltura());
        gc = canvas.getGraphicsContext2D();
        Pane canvasPane = new Pane(canvas);
        root.setCenter(canvasPane);

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
        btnAplicarSol = new Button("Aplicar Sol"); // Botão para aplicar o Sol

        txtX = new TextField();
        txtY = new TextField();
        txtId = new TextField();
        txtType = new TextField();
        txtEsq = new TextField();
        txtDir = new TextField();
        txtCima = new TextField();
        txtBaixo = new TextField();
        txtEnergia = new TextField();
        txtEnergia.setEditable(false); // Campo de energia só leitura

        strenghtSlider = new Slider();

        sidebar.getChildren().addAll(comboBox, btnAddElement, btnCreateEcosystem, btnImport, btnExport, btnDelElement, btnAplicarSol);
        sidebar.getChildren().addAll(new Label("Energia:"), txtEnergia); // Adicionando o campo de energia
        root.setRight(sidebar);

        scene = new Scene(root, 800, 600);

        // Set the menu bar at the top
        root.setTop(new VBox(menuBar, toolbar));
    }

    private void registerHandlers() {
        // Handler para Play/Pause
        btnPlayPause.setOnAction(event -> {
            if (ecossistemaManager.isRunning()) {
                ecossistemaManager.pausarJogo();
                btnPlayPause.setText("Play");
            } else {
                ecossistemaManager.retomarJogo();
                btnPlayPause.setText("Pause");
            }
        });

        // Handler para adicionar elementos
        btnAddElement.setOnAction(event -> {
            String selectedType = comboBox.getSelectionModel().getSelectedItem();
            if (selectedType != null) {
                switch (selectedType) {
                    case "Fauna":
                        ecossistemaManager.adicionarElementoAleatoriamente(Elemento.FAUNA);
                        break;
                    case "Flora":
                        ecossistemaManager.adicionarElementoAleatoriamente(Elemento.FLORA);
                        break;
                    case "Inanimado":
                        ecossistemaManager.adicionarElementoAleatoriamente(Elemento.INANIMADO);
                        break;
                }
                Platform.runLater(this::updateEcosystemDisplay);
            }
        });

        // Handler para criar novo ecossistema
        btnCreateEcosystem.setOnAction(event -> {
            ecossistemaManager.limparElementos();
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

        // Handler para aplicar Sol
        btnAplicarSol.setOnAction(event -> {
            ecossistemaManager.aplicarSol();
            Platform.runLater(this::updateEcosystemDisplay);
        });

        // Handler para aplicar Herbicida
        btnAplicarHerbicida.setOnAction(event -> {
            IElemento elemento = ecossistemaManager.buscarElemento(currentElementIDSelected);
            if (elemento instanceof Flora) {
                ecossistemaManager.aplicarHerbicida((Flora) elemento);
                Platform.runLater(this::updateEcosystemDisplay);
            } else {
                showAlert(Alert.AlertType.WARNING, "Aviso", "Selecione um elemento do tipo Flora para aplicar o herbicida.");
            }
        });

        // Handler para injetar força
        btnInjetarForca.setOnAction(event -> {
            IElemento elemento = ecossistemaManager.buscarElemento(currentElementIDSelected);
            if (elemento instanceof Fauna) {
                ecossistemaManager.injetarForca((Fauna) elemento);
                Platform.runLater(this::updateEcosystemDisplay);
            } else {
                showAlert(Alert.AlertType.WARNING, "Aviso", "Selecione um elemento do tipo Fauna para injetar força.");
            }
        });

        // Handler para exportar elementos para CSV
        exportMenuItem.setOnAction(event -> {
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
            Platform.runLater(this::updateEcosystemDisplay);
        });
    }

//    private void exportarElementosParaCSV(File file) {
//        try (FileWriter writer = new FileWriter(file)) {
//            writer.append("Tipo,Forca,PosX,PosY\n");
////            for (IElemento elemento : ecossistemaManager.obterElementos()) {
////                String tipo = elemento.getClass().getSimpleName();
////                double forca = (elemento instanceof IElementoComForca) ? ((IElementoComForca) elemento).getForca() : 0;
////                double posX = elemento.getArea().esquerda();
////                double posY = elemento.getArea().cima();
////                writer.append(String.format("%s,%.2f,%.2f,%.2f\n", tipo, forca, posX, posY));
////            }
//            ecossistemaManager.exportarElementosParaCSV(file);
//            showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Elementos exportados com sucesso.");
//        } catch (IOException e) {
//            showAlert(Alert.AlertType.ERROR, "Erro", "Erro ao exportar elementos: " + e.getMessage());
//        }
//    }

    private void updateEcosystemDisplay() {
        // Limpa o fundo do canvas
        gc.setFill(Color.web("#373054"));
        gc.fillRect(0, 0, ecossistemaManager.getLargura(), ecossistemaManager.getAltura());

        Collection<IElemento> elementos = ecossistemaManager.obterElementos();

        // Limpar a posição anterior de todos os elementos de Fauna
        for (Map.Entry<Integer, Area> entry : previousPositions.entrySet()) {
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
                Color floraColor = Color.GREENYELLOW;
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
            strenghtSlider.setValue(((Fauna) elemento).getForca());
            txtEnergia.setText(String.valueOf(((Fauna) elemento).getForca())); // Atualiza a energia da Fauna
        } else if (elemento instanceof Flora) {
            strenghtSlider.setValue(((Flora) elemento).getForca());
            txtEnergia.setText(String.valueOf(((Flora) elemento).getForca())); // Atualiza a energia da Flora
        } else {
            txtEnergia.setText("");
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

    public Scene getScene() {
        return scene;
    }
}
