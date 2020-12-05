package ehu.isad.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Utils {

    public static Properties lortuEzarpenak() throws IOException {
        Properties properties = null;

        try (InputStream in = Utils.class.getResourceAsStream(System.getProperty("user.home")+"/"+".whatwebfx")){
            properties = new Properties();
            properties.load(in);

        } catch (
                IOException e) {
            e.printStackTrace();
        }

        return properties;
    }

}