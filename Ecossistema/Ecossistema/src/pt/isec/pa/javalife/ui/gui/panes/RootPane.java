package pt.isec.pa.javalife.ui.gui.panes;


import javafx.scene.layout.BorderPane;

public class RootPane extends BorderPane {

    public RootPane(){
        //Configuração inicial do RootPane
        setTop(new MainMenuUI());
        setCenter(new EcossistemaUI());
    }


}
