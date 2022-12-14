package Domain;

import java.awt.image.BufferedImage;
import java.util.*;

public interface StateInterface {
    public Boolean init();
    public String getMediaInformation(int id);
    public Displayable getMedia(int id);
    public List<? extends Displayable> getDisplayables(); 
    public List<? extends Displayable> getMovieDisplayables(); 
    public List<? extends Displayable> getSeriesDisplayables(); 
    public BufferedImage getMediaPicture(int id);
    public List<? extends Displayable> getGenreList(String genre) throws Exception;   
    public List<? extends Displayable> search(String input) throws Exception;   
    public Boolean setCurUser(int userID); 
    public void addUser(String name, int age, String gender); 
    public void addFavorite(int movieID); 
    public void addFavorite(int movieID, int userID); 
    public void removeFavorite(int movieID);
    public void removeFavorite(int movieID, int usrID);
    public Boolean isFavorite(int movieID); 
    public Boolean isFavorite(int movieID, int usrID);
    public void addWatched(int movieID); 
    public void addWatched(int movieID, int userID);
    public void writeUsers(); 
    public Set<String> getGenres(); 
    public UserInterface getUser();


}