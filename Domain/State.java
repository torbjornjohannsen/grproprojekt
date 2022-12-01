package Domain;

import java.awt.image.BufferedImage;
import java.text.NumberFormat;
import java.util.*;

import Data.PictureDataAccess;
import Data.PictureDataAccessInterface;
import Data.TextDataAccess;
import Data.TextDataAccessInterface;


public class State implements StateInterface {
    protected List<Series> series;
    protected List<Movie> movies;
    protected List<Media> medias; 
    protected List<User> users; 
    protected int curUserID; 
    private NumberFormat numFormat; 

    public State() 
    {
        series = new ArrayList<>(); 
        movies = new ArrayList<>(); 
        medias = new ArrayList<>(); 
        users = new ArrayList<>(); 
        curUserID = 0; 
        users.add(new User("Test mcDefault", 69, "Attack Helicopter"));

        numFormat = NumberFormat.getInstance(Locale.FRANCE); 
    }

    public Boolean init() {
        TextDataAccessInterface tLoader = new TextDataAccess(); 
        PictureDataAccessInterface pLoader = new PictureDataAccess(); 

        Boolean moviesRes = InitMediaType(tLoader.load("film"), pLoader);
        Boolean seriesRes = InitMediaType(tLoader.load("serier"), pLoader);

        return moviesRes && seriesRes;
    }

    public String getMediaInformation(int id) {
        return medias.get(id).getTitle();
    }

    public BufferedImage getMediaPicture(int id) {
        return medias.get(id).getPicture(); 
    }

    public List<? extends Displayable> getDisplayables() {
        List<? extends Displayable> displayList = medias; 
        return displayList; 
    }

    public List<? extends Displayable> getMovieDisplayables() {
        List<? extends Displayable> displayList = movies; 
        return displayList; 
    }

    public List<? extends Displayable> getSeriesDisplayables() {
        List<? extends Displayable> displayList = series; 
        return displayList; 
    }

    public List<String> getGenreList(String genre) {
        return null; 
    }  

    public List<Media> search(String input) {
        for(Media media : medias) {
            if(media.title.toLowerCase().contains(input.toLowerCase())) {
                System.out.println(media.title);
            }
        }
        return null; 
    } 

    public void AddUser(String name, int age, String gender) {
        users.add(new User(name, age, gender));
    }

    public Boolean SetCurUser(int userID) {
        try {
            users.get(userID); 
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Tried to set an invalid userID");
            return false; 
        }
        curUserID = userID;
        return true; 
    }

    public void AddFavorite(int movieID) {
        users.get(curUserID).AddFavorite(movieID);
    }

    public void AddFavorite(int movieID, int userID) {
        try {
            users.get(userID).AddFavorite(movieID);
        } catch (IndexOutOfBoundsException e) {
            
            System.out.println("Tried to add a favorite for an invalid user");
        }
    }

    private Boolean InitMediaType(List<String> media, PictureDataAccessInterface pLoader) {
        for (String s : media) {
            s = s.trim(); 
            String[] fields = s.split(";"); 

            if(fields.length < 4 || fields.length > 5) { throw new IllegalArgumentException("Invalid lines in media: " + s); }

            BufferedImage image = pLoader.Load(fields[0]); 

            String[] genresArr = fields[2].split(",");    
            List<String> genres = Arrays.asList(genresArr); 
            
            double whyyyy; 
            // Fucking comma seperated numbers, its either this or modifying the entire fucking text files to use a sensible, standard decimel seperator
            try {
                fields[3] = fields[3].trim();
                whyyyy = numFormat.parse(fields[3]).doubleValue();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return null; 
            }

            if(fields.length == 4 ) // its a movie
            {
                Movie m = new Movie(medias.size(), fields[0], fields[1], genres, whyyyy, image);
                movies.add(m); 
                medias.add(m);
            } else { // its a series //TODO make this an if statement and make the else throw an error
                String[] seasonsArr = fields[4].split(","); 
                Map<Integer, Integer> seasonMap = new HashMap<>(); 
                for(String season : seasonsArr)
                {
                    String[] seasonS = season.split("-"); 
                    seasonMap.put(Integer.parseInt(seasonS[0].trim()), Integer.parseInt(seasonS[1].trim()));
                }
                Series serie = new Series(medias.size(), fields[0], fields[1], genres, whyyyy, image, seasonMap);
                series.add(serie); 
                medias.add(serie); 
            }
        }
        return true; 
    }

}
