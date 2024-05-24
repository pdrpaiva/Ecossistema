package pt.isec.pa.javalife.ui.gui.panes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pt.isec.pa.javalife.model.facade.JavaLifeFacade;

public class SettingsUI extends VBox {
    private final JavaLifeFacade facade;
    private final Stage primaryStage;

    public SettingsUI(JavaLifeFacade facade, Stage primaryStage) {
        this.facade = facade;
        this.primaryStage = primaryStage;

        Button btnSaveSettings = new Button("Save Settings");
        Button btnBack = new Button("Back");

        btnSaveSettings.setOnAction(event -> saveSettings());
        btnBack.setOnAction(event -> goBack());

        this.setAlignment(Pos.CENTER);
        this.setSpacing(10);
        this.getChildren().addAll(btnSaveSettings, btnBack);
    }

    private void saveSettings() {
        // Lógica para salvar as configurações
    }

    private void goBack() {
        MainMenuUI mainMenu = new MainMenuUI(facade, primaryStage);
        Scene scene = new Scene(mainMenu, 800, 600);
        primaryStage.setScene(scene);
    }
}
