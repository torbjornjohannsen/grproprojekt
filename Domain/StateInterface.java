package Domain;

import java.awt.image.BufferedImage;
import java.util.*;

public interface StateInterface {
    public Boolean init();
    public String getMediaInformation(int id);
    public List<? extends Displayable> getDisplayables(); 
    public List<? extends Displayable> getMovieDisplayables(); 
    public List<? extends Displayable> getSeriesDisplayables(); 
    public BufferedImage getMediaPicture(int id);
    public List<? extends Displayable> getGenreList(String genre);   
    public List<? extends Displayable> search(String input);   
    public Boolean SetCurUser(int userID); 
    public void AddUser(String name, int age, String gender); 
    public void AddFavorite(int movieID); 
    public void AddFavorite(int movieID, int userID); 
    public void AddWatched(int movieID); 
    public void AddWatched(int movieID, int userID);
    public void WriteUsers(); 
    public Set<String> getGenres(); 


}

/* Bør vi blot fjerne dette interface, da det ikke i grunden bliver brugt? */