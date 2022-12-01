package Domain;

import java.awt.image.BufferedImage;
import java.util.List;

public interface StateInterface {
    public Boolean init();
    public String getMediaInformation(int id);
    public List<? extends Displayable> getDisplayables(); 
    public List<? extends Displayable> getMovieDisplayables(); 
    public List<? extends Displayable> getSeriesDisplayables(); 
    public BufferedImage getMediaPicture(int id);
    public List<String> getGenreList(String genre);   
    public List<? extends Displayable> search(String input);   
    public Boolean SetCurUser(int userID); 
    public void AddFavorite(int movieID); 
}
