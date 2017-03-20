package View;

/**
 * Created by hugo on 16/03/2017.
 */
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ImagePanel extends JPanel{

    private BufferedImage bImage;

    public ImagePanel(String imageLink) {
        try {
            URL imageURL = new URL(imageLink);
            bImage = ImageIO.read(imageURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bImage, 0, 0, this); // see javadoc for more info on the parameters
    }
}