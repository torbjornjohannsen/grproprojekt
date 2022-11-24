package Domain;
import java.util.List;
import java.util.Map;

public class Series extends Media {
    Map<Integer, Integer> seasons;

    Series (int id, String title, int year, List<String> genre, double rating, BufferedImage image, Map<Integer, Integer> seasons) {
        super(id, title, year, genre, rating, image);
        this.seasons = seasons;
    }
}
