package Domain;

import java.util.List;

import javax.swing.JPanel;
import java.awt.Color;

import java.awt.image.BufferedImage;

public abstract class Media implements Displayable {
    int id;
    String title;
    String year;
    List<String> genre;
    double rating;
    BufferedImage image;

    Media (int id, String title, String year, List<String> genre, double rating, BufferedImage image) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.rating = rating;
        this.image = image;
    }

    public int getId() {return id;}
    public String getTitle() {return title;}
    public String getYear() {return year;}
    public List<String> getGenre() {return genre;}
    public double getRating() {return rating;}
    public BufferedImage getPicture() {return image;}
    public String getStartYear() {return getYear().split("-")[0].trim();}

    public JPanel display() {
        JPanel panel = new JPanel();

        panel.setBackground(Color.GRAY);

        return panel;
    }
    
}

