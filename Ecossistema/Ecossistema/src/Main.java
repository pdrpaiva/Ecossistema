
import javafx.application.Application;
import pt.isec.pa.javalife.model.gameengine.GameEngine;
import pt.isec.pa.javalife.model.gameengine.IGameEngine;
import pt.isec.pa.javalife.ui.gui.JavaLifeFX;

import static javafx.application.Application.*;


public class Main {
    public static void main(String[] args) {
        //System.out.println("Hello");
       // Application.launch(JavaLifeFX.class,args);
       // IGameEngine gameEngine = new GameEngine();
       // TestClient client = new TestClient();
       // gameEngine.registerClient(client);
       // gameEngine.start(500);
       // gameEngine.waitForTheEnd();
        Application.launch(JavaLifeFX.class,args);
    }
}
