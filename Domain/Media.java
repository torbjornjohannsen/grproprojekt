package Domain;
import java.util.List;
import java.awt.image.BufferedImage;
import java.io.File;

public abstract class Media {
    int id;
    String title;
    String year; // since series have ranges of years, can be 1990-1991 or 2011- for instance, all we do is print them so string is fine
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

}

