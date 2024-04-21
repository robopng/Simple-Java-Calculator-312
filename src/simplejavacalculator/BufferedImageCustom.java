package simplejavacalculator;

import java.io.*;
import javax.imageio.ImageIO;
import java.awt.*;

/**
 * This class will return an image
 * from a binary data.
 */
class BufferedImageCustom {
    public Image imageReturn()
            throws IOException {
        Image image;

        InputStream bis = getClass().getResourceAsStream("/resources/icon/icon.png");
        image = ImageIO.read(bis);  // pray it's not null

        return image;
    }
}
