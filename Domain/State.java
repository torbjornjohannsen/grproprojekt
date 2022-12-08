package Domain;

import java.awt.image.BufferedImage;
import java.text.NumberFormat;
import java.text.ParseException;
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
    private Map<String, List<Media>> genreMap; // created when needed
    private NavigableMap<String, Media> titleMap; // a sub-interface of SortedMap
    private List<String> titleList;

    public State() 
    {
        series = new ArrayList<>(); 
        movies = new ArrayList<>(); 
        medias = new ArrayList<>(); 
        users = new ArrayList<>(); 
        genreMap = new HashMap<>();
        titleMap = new TreeMap<String,Media>(); 
        titleList = new ArrayList<>(); 
        curUserID = 0; 

        numFormat = NumberFormat.getInstance(Locale.FRANCE); 
    }

    public Boolean init() {
        TextDataAccessInterface tLoader = new TextDataAccess(); 
        PictureDataAccessInterface pLoader = new PictureDataAccess(); 

        Boolean moviesRes = InitMediaType(tLoader.load("film"), pLoader);
        Boolean seriesRes = InitMediaType(tLoader.load("serier"), pLoader);
        Boolean usersRes = initUsers(tLoader.load("users"));    

        
        for(Media m : medias) {
            titleMap.put(cleanSearchString(m.title), m);
            titleList.add(cleanSearchString(m.title)); 

            List<String> mediaGenres = m.getGenre(); 
            for (String g : mediaGenres) {
                if(genreMap.containsKey(g)) { genreMap.get(g).add(m); }
                else { 
                    List<Media> mediaList = new ArrayList<>(); 
                    mediaList.add(m); 
                    genreMap.put(g, mediaList);
                }
            }
        }

        Collections.sort(titleList);

        /* System.out.println("Sorted title list: "); 
        for(String s : titleList) {
            System.out.println(s);
        } */

        return moviesRes && seriesRes && usersRes;
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

    public List<? extends Displayable> getGenreList(String genre) {
        return genreMap.get(genre); 
    }  

    public List<? extends Displayable> search(String input) {
        ArrayList<Media> results = new ArrayList<>();
        /* for(Media media : medias) {
            if(media.getTitle().toLowerCase().contains(input.toLowerCase()) || 
        input.toLowerCase().contains(media.getTitle().toLowerCase()) || 
        input.contains(media.getStartYear())) {
                results.add(media);
            }
        } */

        List<String> matches = containsSearch(input.toUpperCase(), titleList);
        if(matches == null) { return results; }
        /* System.out.println("Matches for " + input);
        for(String s : matches) {
            System.out.println("- " + s);
        } */

        NavigableMap<String, Media> resMap = titleMap.subMap(matches.get(0), true, matches.get(matches.size() - 1), true);

        for (Map.Entry<String, Media> m : resMap.entrySet()) {
            results.add(m.getValue());
        }
        return results; 
    }

    private List<String> containsSearch(String input, List<String> prevList) {
        int midIndex = prevList.size() / 2; 
        //System.out.println("midIndex: " + midIndex); 
        if(midIndex < 2) { return null; }
        String midVal = prevList.get(midIndex); 
        if(midVal.contains(input)) {
            //System.out.println("matches");
            int upper, lower; 
            upper = lower = midIndex; 

            //Iterate upwards 
            for(int i = midIndex; i < prevList.size(); i++) {
                if(!prevList.get(i).contains(input)) { break; }
                upper = i; 
            }

            //Iterate downwards 
            for(int i = midIndex; i > 0; i--) {
                if(!prevList.get(i).contains(input)) { break; }
                lower = i; 
            }

            return prevList.subList(lower, upper + 1); 
        } else if(input.compareTo(midVal) >= 0) { // input greater than or equal to midVal 
            //System.out.println("higher");
            return containsSearch(input, prevList.subList(midIndex, prevList.size())); // so look at the upper half of the list
        } else { // lower
            //System.out.println("lower");
            return containsSearch(input, prevList.subList(0, midIndex + 1)); // so look at the lower half
        }
    }

    private String cleanSearchString(String in) {
        in = " " + in + " "; // to make sure all seperate words have spaces in front and back 
        in = in.toUpperCase(); 
        // Remove common words with no real meaning
        in = in.replaceAll(" THE | A | I | AN | YOU | OF | AND | IN | TO | WE ", "");
        in = in.replaceAll(" ", ""); // finally remove spaces
        return in; 
    }

    public List<? extends Displayable> sortYear() {
        List<Media> displayList = medias; 
        displayList.sort((o1, o2) -> o1.getStartYear().compareTo(o2.getStartYear()));
        return displayList;
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
        try {
            users.get(curUserID).AddFavorite(movieID);
        } catch (IndexOutOfBoundsException e) {

            System.out.println("Tried to add a favorite for an invalid current user: " + curUserID);
        }
    }

    public void AddFavorite(int movieID, int userID) {
        try {
            users.get(userID).AddFavorite(movieID);
        } catch (IndexOutOfBoundsException e) {

            System.out.println("Tried to add a favorite for an invalid user: " + userID);
        }
    }

    public void AddWatched(int movieID) {
        try {
            users.get(curUserID).AddWHistory(movieID);
        } catch (IndexOutOfBoundsException e) {

            System.out.println("Tried to add watched for an invalid current user: " + curUserID);
        }
    }

    public void AddWatched(int movieID, int userID) {
        try {
            users.get(userID).AddWHistory(movieID);
        } catch (IndexOutOfBoundsException e) {

            System.out.println("Tried to add watched for an invalid user: " + userID);
        }
    }

    @Override
    protected void finalize() throws Throwable //destructor
    {
        WriteUsers();
    }

    public void WriteUsers() {
        TextDataAccessInterface writer = new TextDataAccess(); 
        List<String> output = new ArrayList<>(); 
        for (User user : users) {
            String line = user.getName() + "; " + user.getAge() + "; " + user.getGender() + "; ";
            Set<Integer> watchHistory = user.getWatchHistory();
            int i = 0; 
            for (Integer watchedID : watchHistory) {
                line += watchedID + (i + 1 == watchHistory.size() ? "" : ", ");
                i++; 
            }
            line += "; "; 
            i = 0; 
            Set<Integer> favorites = user.getFavoriteList();
            for (Integer fID : favorites) {
                line += fID + (i + 1 == favorites.size() ? "" : ", "); 
                i++; 
            } 
            line += ";";
            output.add(line);
        }
        writer.Write(output, "users");
    }

    private Boolean InitMediaType(List<String> media, PictureDataAccessInterface pLoader) {

        for (String s : media) {
            s = s.trim(); 
            String[] fields = s.split(";"); 

            if(fields.length < 4 || fields.length > 5) { throw new IllegalArgumentException("Invalid lines in media: " + s); }

            BufferedImage image = pLoader.Load(fields[0]); 

            String[] genresArr = fields[2].split(", ");
            genresArr[0] = genresArr[0].substring(1);
            
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
            } else { // its a series //TODO make this an if statement and make the else throw an error; Already did that, look at the initial if statement
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

    private Boolean initUsers(List<String> userList) {
        for (String s : userList) {
            s = s.trim(); 
            String[] fields = s.split(";"); 

            if(fields.length != 5) { throw new IllegalArgumentException("Invalid user-string"); }

            users.add(new User(fields[0], Integer.parseInt(fields[1].trim()), fields[2]));

            String[] watched = fields[4].split(","); 
            if(watched.length > 1) { // split just returns the same string if no matches, and its concievable that a user has no watched or favorites
                for(String w : watched) {
                    users.get(users.size() - 1).AddWHistory(Integer.parseInt(w.trim()));
                }
            }

            String[] favorites = fields[4].split(","); 
            if(favorites.length > 1) {
                for(String f : favorites) {
                    users.get(users.size() - 1).AddWHistory(Integer.parseInt(f.trim()));
                }
            }
        }
        return true; 
    }

    public Set<String> getGenres() {
        return genreMap.keySet(); 
    }

}
