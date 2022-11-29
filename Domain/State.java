package Domain;

import java.awt.image.BufferedImage;
import java.text.NumberFormat;
import java.util.*;

import Data.PictureDataAccess;
import Data.PictureDataAccessInterface;


public class State implements StateInterface {
    protected List<Series> series;
    protected List<Movie> movies;
    protected List<Media> medias; 
    private NumberFormat numFormat; 

    public State() 
    {
        series = new ArrayList<>(); 
        movies = new ArrayList<>(); 
        medias = new ArrayList<>(); 

        numFormat = NumberFormat.getInstance(Locale.FRANCE); 
    }

    public Boolean init(List<String> media) {
        PictureDataAccessInterface pLoader = new PictureDataAccess(); 
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
            } else { // its a series
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

    public String getMediaInformation(int id) {
        return medias.get(id).getTitle();
    }

    public BufferedImage getMediaPicture(int id) {
        return medias.get(id).getPicture(); 
    }

    public List<Displayable> getDisplayables() {
        return null; 
    }

    public List<Media> getGenreList(String genre) {
        return null; 
    }  

    public List<Media> search(String input) {
        return null; 
    } 

}
