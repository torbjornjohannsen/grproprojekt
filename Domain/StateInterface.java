package Domain;

import java.awt.image.BufferedImage;
import java.util.List;

public interface StateInterface {
    public Boolean init();
    public String getMediaInformation(int id);
    public List<? extends Displayable> getDisplayables(); 
    public BufferedImage getMediaPicture(int id);
    public List<Media> getGenreList(String genre);   
    public List<Media> search(String input);   
}
