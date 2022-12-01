package Domain;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.GridLayout;
import java.awt.image.*;

public class Movie extends Media{
    public Movie(int id, String title, String year, List<String> genre, double rating, BufferedImage image) {
        super(id, title, year, genre, rating, image);
    }

    @Override 
    public JPanel display() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout());
            BufferedImage img = image;
            ImageIcon icon = new ImageIcon(img);
            JButton button = new JButton(icon);
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            panel.add(button);

        return panel;
    }
}
