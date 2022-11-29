package Domain;
import java.util.List;
import java.awt.image.*;

public class Movie extends Media {
    public Movie(int id, String title, int year, List<String> genre, double rating, BufferedImage image) {
        super(id, title, year, genre, rating, image);
    }
}
