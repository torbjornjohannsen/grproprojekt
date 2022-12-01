package Domain;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.GridLayout;

import java.awt.image.*;

public class Series extends Media{
    Map<Integer, Integer> seasons;

    
    public Series (int id, String title, String year, List<String> genre, double rating, BufferedImage image, Map<Integer, Integer> seasons) {
        super(id, title, year, genre, rating, image);
        this.seasons = seasons;
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
