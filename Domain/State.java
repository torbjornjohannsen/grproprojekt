package Domain;

import java.awt.image.BufferedImage;
import java.util.List;

public class State implements StateInterface {
    protected List<Series> series;
    protected List<Movie> movies;

    public Boolean init(List<String> media) {
        return true;
    }

    public String getMediaInformation(int id) {
        return "";
    }

    public BufferedImage getMediaPicture(int id) {
        
    }

}
