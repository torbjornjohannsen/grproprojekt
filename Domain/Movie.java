package Domain;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.image.*;
import java.io.File;
import java.io.IOException;

public class Movie extends Media implements Displayable {
    public Movie(int id, String title, String year, List<String> genre, double rating, BufferedImage image) {
        super(id, title, year, genre, rating, image);
    }

    @Override 
    public JPanel display() {
        JPanel panel = new JPanel();
        try {
            BufferedImage img = ImageIO.read(new File("./MedieData/pictures/12 Angry Men.jpg"));
            ImageIcon icon = new ImageIcon(img);
            JButton button = new JButton(icon);
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            panel.add(button);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return panel;
    }
}
