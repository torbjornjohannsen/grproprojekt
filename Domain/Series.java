package Domain;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import java.awt.image.*;

public class Series extends Media implements Displayable{
    Map<Integer, Integer> seasons;

    
    public Series (int id, String title, String year, List<String> genre, double rating, BufferedImage image, Map<Integer, Integer> seasons) {
        super(id, title, year, genre, rating, image);
        this.seasons = seasons;
    }

    public JPanel display() {
        JPanel panel = new JPanel();

        return panel;
    }
}
