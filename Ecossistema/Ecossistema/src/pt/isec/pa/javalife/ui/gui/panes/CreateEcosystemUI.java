    package pt.isec.pa.javalife.ui.gui.panes;

    import javafx.geometry.Insets;
    import javafx.geometry.Pos;
    import javafx.scene.Scene;
    import javafx.scene.control.Button;
    import javafx.scene.control.Label;
    import javafx.scene.control.Slider;
    import javafx.scene.control.TextField;
    import javafx.scene.image.Image;
    import javafx.scene.image.ImageView;
    import javafx.scene.layout.GridPane;
    import javafx.scene.layout.HBox;
    import javafx.scene.layout.VBox;
    import javafx.scene.paint.Color;
    import javafx.scene.text.Font;
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
            GridPane root = new GridPane();
            root.setStyle("-fx-background-color: #4B0082;");
            root.setPadding(new Insets(20));
            root.setHgap(20);
            root.setVgap(20);

            // Title
            Label title = new Label("Criação do Ecossistema");
            title.setTextFill(Color.WHITE);
            title.setFont(new Font(24));
            root.add(title, 0, 0, 2, 1);

            // Name
            Label nameLabel = new Label("Nome");
            nameLabel.setTextFill(Color.WHITE);
            nameLabel.setFont(new Font(16));
            TextField nameField = new TextField();
            root.add(nameLabel, 0, 1);
            root.add(nameField, 1, 1);

            // Dimensions
            Label heightLabel = new Label("Altura");
            heightLabel.setTextFill(Color.WHITE);
            heightLabel.setFont(new Font(16));
            TextField heightField = new TextField("300");
            root.add(heightLabel, 0, 2);
            root.add(heightField, 1, 2);

            Label widthLabel = new Label("Comprimento");
            widthLabel.setTextFill(Color.WHITE);
            widthLabel.setFont(new Font(16));
            TextField widthField = new TextField("300");
            root.add(widthLabel, 0, 3);
            root.add(widthField, 1, 3);

            // Quantities
            Label faunaLabel = new Label("Quantidade Fauna");
            faunaLabel.setTextFill(Color.WHITE);
            faunaLabel.setFont(new Font(16));
            TextField faunaField = new TextField("10");
            root.add(faunaLabel, 0, 4);
            root.add(faunaField, 1, 4);

            Label floraLabel = new Label("Quantidade Flora");
            floraLabel.setTextFill(Color.WHITE);
            floraLabel.setFont(new Font(16));
            TextField floraField = new TextField("10");
            root.add(floraLabel, 0, 5);
            root.add(floraField, 1, 5);

            Label inanimateLabel = new Label("Quantidade Inanimados");
            inanimateLabel.setTextFill(Color.WHITE);
            inanimateLabel.setFont(new Font(16));
            TextField inanimateField = new TextField("10");
            root.add(inanimateLabel, 0, 6);
            root.add(inanimateField, 1, 6);

            // Time unit
            Label timeUnitLabel = new Label("Unidade de tempo");
            timeUnitLabel.setTextFill(Color.WHITE);
            timeUnitLabel.setFont(new Font(16));
            Slider timeUnitSlider = new Slider(100, 1000, 100);
            root.add(timeUnitLabel, 0, 7);
            root.add(timeUnitSlider, 1, 7);

            // Icons
            VBox iconsBox = new VBox(10);
            iconsBox.setAlignment(Pos.CENTER);
            Label iconsLabel = new Label("Selecione um ícone para a fauna");
            iconsLabel.setTextFill(Color.WHITE);
            iconsLabel.setFont(new Font(16));
            iconsBox.getChildren().add(iconsLabel);

            HBox iconsRow = new HBox(10);
            iconsRow.setAlignment(Pos.CENTER);

    //        ImageView wolfIcon = new ImageView(new Image("path/to/wolf.png"));
    //        wolfIcon.setFitWidth(50);
    //        wolfIcon.setFitHeight(50);
    //        ImageView sheepIcon = new ImageView(new Image("path/to/sheep.png"));
    //        sheepIcon.setFitWidth(50);
    //        sheepIcon.setFitHeight(50);
    //        ImageView bearIcon = new ImageView(new Image("path/to/bear.png"));
    //        bearIcon.setFitWidth(50);
    //        bearIcon.setFitHeight(50);
    //        ImageView snakeIcon = new ImageView(new Image("path/to/snake.png"));
    //        snakeIcon.setFitWidth(50);
    //        snakeIcon.setFitHeight(50);
    //
    //        iconsRow.getChildren().addAll(wolfIcon, sheepIcon, bearIcon, snakeIcon);
            iconsBox.getChildren().add(iconsRow);

            root.add(iconsBox, 2, 1, 1, 7);

            // Create button
            createButton = new Button("Criar");
            root.add(createButton, 0, 8, 2, 1);

            scene = new Scene(root, 800, 600);
        }

        private void registerHandlers() {
            // Add event handlers if needed
            createButton.setOnAction(event -> {
                EcosystemUI ecosystemUI = new EcosystemUI(primaryStage);
                primaryStage.setScene(ecosystemUI.getScene());
            });
        }

        public Scene getScene() {
            return scene;
        }
    }
