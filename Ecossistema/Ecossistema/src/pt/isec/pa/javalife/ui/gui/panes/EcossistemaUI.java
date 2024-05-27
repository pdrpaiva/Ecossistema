package pt.isec.pa.javalife.ui.gui.panes;

import javafx.scene.layout.Pane;
import pt.isec.pa.javalife.model.facade.JavaLifeFacade;

public class EcossistemaUI extends Pane {
    private final JavaLifeFacade facade;

    public EcossistemaUI(JavaLifeFacade facade) {
        this.facade = facade;
        // Configuração inicial da visualização do ecossistema
    }
}