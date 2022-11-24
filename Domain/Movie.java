package Domain;
import java.util.List;

public class Movie extends Media {
    Movie(int id, String title, int year, List<String> genre, double rating, BufferedImage image) {
        super(id, title, year, genre, rating, image);
    }
}
