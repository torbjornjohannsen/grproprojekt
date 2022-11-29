package Domain;
import java.util.List;

import javax.swing.JPanel;

import java.awt.image.*;

public class Movie extends Media implements Displayable {
    public Movie(int id, String title, int year, List<String> genre, double rating, BufferedImage image) {
        super(id, title, year, genre, rating, image);
    }

    @Override 
    public JPanel display() {
        JPanel panel = new JPanel();

        return panel;
    }
}
