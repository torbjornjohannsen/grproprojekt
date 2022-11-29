package Domain;
import java.util.List;
import java.awt.image.*;

public class Movie extends Media {
    Movie(int id, String title, String year, List<String> genre, double rating, BufferedImage image) {
        super(id, title, year, genre, rating, image);
    }
}
