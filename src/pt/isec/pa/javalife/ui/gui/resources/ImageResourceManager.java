package pt.isec.pa.javalife.ui.gui.resources;



import java.util.HashMap;
import javafx.scene.image.Image;

public class ImageResourceManager {
    private static final HashMap<String, Image> resources = new HashMap<>();

    private ImageResourceManager() {}

    public static Image getImage(String resourceName) {
        Image image = resources.get(resourceName);
        if (image == null) {
            // Carregar a imagem correspondente ao recurso
            String imagePath = "/pt/isec/pa/javalife/ui/gui/resources/images/" + resourceName + ".png";
            image = new Image(ImageResourceManager.class.getResourceAsStream(imagePath));
            resources.put(resourceName, image);
        }
        return image;
    }
}