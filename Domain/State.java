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
    private Map<String, List<Media>> genreMap; 
    private NavigableMap<String, List<Media>> titleWordMap; // a sub-interface of SortedMap
    private NavigableMap<Integer, List<Media>> yearMap; 
    private List<String> titleWordList;

    public State() 
    {
        series = new ArrayList<>(); 
        movies = new ArrayList<>(); 
        medias = new ArrayList<>(); 
        users = new ArrayList<>(); 
        genreMap = new HashMap<>();
        titleWordMap = new TreeMap<>(); 
        titleWordList = new ArrayList<>(); 
        yearMap = new TreeMap<>(); 
        curUserID = 0; 

        // Got this from https://stackoverflow.com/a/4323628
        // Use france since they also use comma as decimal seperator and are one of the constants in locale
        numFormat = NumberFormat.getInstance(Locale.FRANCE); 
    }

    public Boolean init() {
        TextDataAccessInterface tLoader = new TextDataAccess();
        PictureDataAccessInterface sPLoader = new PictureDataAccess("serieforsider"); 
        PictureDataAccessInterface mPLoader = new PictureDataAccess("filmplakater"); 

        Boolean moviesRes = initMediaType(tLoader.load("film"), mPLoader);
        Boolean seriesRes = initMediaType(tLoader.load("serier"), sPLoader);
        Boolean usersRes = initUsers(tLoader.load("users"));    

        
        for(Media media : medias) {
            String[] titleWords = cleanSearchString(media.title);
            for(String word : titleWords) {
                
                if(titleWordMap.containsKey(word)) { // faster than checking the list since its sorted
                    titleWordMap.get(word).add(media); 
                } else {
                    titleWordList.add(word); 

                    List<Media> mediaList = new ArrayList<>(); 
                    mediaList.add(media); 

                    titleWordMap.put(word, mediaList); 
                }
            }
            
            String[] years = media.getYear().trim().split("-"); 
            
            for(String currentYear : years) {
                currentYear = currentYear.trim();
                int year = Integer.parseInt(currentYear); 
                if(yearMap.containsKey(year)) {
                    yearMap.get(year).add(media); 
                } else {
                    List<Media> mediaList = new ArrayList<>(); 
                    mediaList.add(media);
                    yearMap.put(year, mediaList);
                }
            }

            List<String> mediaGenres = media.getGenre(); 
            for (String genre : mediaGenres) {
                if(genreMap.containsKey(genre)) { genreMap.get(genre).add(media); }
                else { 
                    List<Media> mediaList = new ArrayList<>(); 
                    mediaList.add(media); 
                    genreMap.put(genre.trim(), mediaList);
                }
            }
        }

        Collections.sort(titleWordList);

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

    public List<? extends Displayable> getGenreList(String genre) throws Exception {
        List<? extends Displayable> result = genreMap.get(genre); 

        if(result == null) throw new Exception("Result must be non-empty!");

        return result;
    }  

    public List<? extends Displayable> search(String input) throws Exception {
        Set<Media> resSet = new HashSet<>(); 
        String[] words; 

        try {
            words = cleanSearchString(input); 
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null; 
        }

        List<String> titleMatches = new ArrayList<>();
        int matchCounter = 0; 
        for(String word : words ) {
            List<String> match; 
            if(word.matches("\\d+")) {
                int year = Integer.parseInt(word.trim()); 
                if(yearMap.containsKey(year)) {
                    for(Media media : yearMap.get(year)) { resSet.add(media); matchCounter++; }
                }
            } else {
                match = containsSearch(word, titleWordList); 
                if(match == null) { continue; }
                titleMatches.addAll(match);
            }
        }

        if(matchCounter + titleMatches.size() == 0) { return null; } 
        
        Collections.sort(titleMatches); 

        for(String s : titleMatches) {
            for (Media media : titleWordMap.get(s)) {
                resSet.add(media);
            }
        }

        return new ArrayList<Media>(resSet); 
    }

    private List<String> containsSearch(String input, List<String> prevList) {
        int midIndex = prevList.size() / 2; 
        String midVal = prevList.get(midIndex); 

        if(midVal.contains(input)) {
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
        }
        
        if(prevList.size() < 2) { return null; }

        if(input.compareTo(midVal) >= 0) { // input greater than or equal to midVal 
            return containsSearch(input, prevList.subList(midIndex, prevList.size())); // so look at the upper half of the list
        } else { // lower
            return containsSearch(input, prevList.subList(0, midIndex + (midIndex > 1 ? 1 : 0))); // so look at the lower half
        }
    }

    private String[] cleanSearchString(String input) {
        input = " " + input + " "; // to make sure all seperate words have spaces in front and back 
        input = input.toUpperCase(); 
        input = input.replaceAll(" ", "  "); // double space so we can remove double invalids
        // Remove common words with no real meaning
        input = input.replaceAll(" THE | A | I | AN | YOU | OF | AND | IN | TO | WE |'S| IT'S | IT |,|\\.|-| ALL |&|;", " ");
        input = input.trim(); 
        String[] out = input.split(" +"); 
        for(String s : out) {
            s = s.trim();
            if(s.isEmpty()) { throw new InputMismatchException("invalid input: " + s + " in cleanSearchString"); }
        }
        return out; 
    }

    public Boolean isFavorite(int movieID) {
        return users.get(curUserID).getFavoriteList().contains(movieID);
    }

    public Boolean isFavorite(int movieID, int usrID) {
        return users.get(usrID).getFavoriteList().contains(movieID);
    }

    public void removeFavorite(int movieID) {
        users.get(curUserID).removeFavorite(movieID);
    }

    public void removeFavorite(int movieID, int usrID) {
        users.get(usrID).removeFavorite(movieID);
    }

    public void addUser(String name, int age, String gender) {
        users.add(new User(name, age, gender));
    }

    public Boolean setCurUser(int userID) {
        try {
            users.get(userID); 
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Tried to set an invalid userID");
            return false; 
        }
        curUserID = userID;
        return true; 
    }

    public void addFavorite(int movieID) {
        try {
            users.get(curUserID).addFavorite(movieID);
        } catch (IndexOutOfBoundsException e) {

            System.out.println("Tried to add a favorite for an invalid current user: " + curUserID);
        }
    }

    public void addFavorite(int movieID, int userID) {
        try {
            users.get(userID).addFavorite(movieID);
        } catch (IndexOutOfBoundsException e) {

            System.out.println("Tried to add a favorite for an invalid user: " + userID);
        }
    }

    public void addWatched(int movieID) {
        try {
            users.get(curUserID).addWHistory(movieID);
        } catch (IndexOutOfBoundsException e) {

            System.out.println("Tried to add watched for an invalid current user: " + curUserID);
        }
    }

    public void addWatched(int movieID, int userID) {
        try {
            users.get(userID).addWHistory(movieID);
        } catch (IndexOutOfBoundsException e) {

            System.out.println("Tried to add watched for an invalid user: " + userID);
        }
    }

    @Override
    protected void finalize() throws Throwable 
    {
        writeUsers(); 
    }

    public void writeUsers() {
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
        writer.write(output, "users");
    }

    private Boolean initMediaType(List<String> media, PictureDataAccessInterface pLoader) {

        for (String s : media) {
            s = s.trim(); 
            String[] fields = s.split(";"); 

            if(fields.length < 4 || fields.length > 5) { throw new IllegalArgumentException("Invalid lines in media: " + s); }

            BufferedImage image = pLoader.load(fields[0]); 

            String[] genresArr = fields[2].split(", ");
            genresArr[0] = genresArr[0].substring(1);
            
            List<String> genres = Arrays.asList(genresArr); 
            
            double rating; 
            try {
                fields[3] = fields[3].trim();
                rating = numFormat.parse(fields[3]).doubleValue();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return false; 
            }

            if(fields.length == 4 ) // its a movie
            {
                Movie m = new Movie(medias.size(), fields[0], fields[1], genres, rating, image);
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
                Series serie = new Series(medias.size(), fields[0], fields[1], genres, rating, image, seasonMap);
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

            users.add(new User(fields[0], Integer.parseInt(fields[1].trim()), fields[2].trim()));

            String[] watched = fields[3].split(","); 
            if(watched.length > 1) { // split just returns the same string if no matches, and its concievable that a user has no watched or favorites
                for(String w : watched) {
                    users.get(users.size() - 1).addWHistory(Integer.parseInt(w.trim()));
                }
            }

            String[] favorites = fields[4].split(","); 
            if(favorites.length > 1) {
                for(String favorite : favorites) {
                    users.get(users.size() - 1).addFavorite(Integer.parseInt(favorite.trim()));
                }
            }
        }
        return true; 
    }

    public Set<String> getGenres() {
        return genreMap.keySet(); 
    }

    public UserInterface getUser() {
        return users.get(curUserID);
    }

    public Displayable getMedia(int id) {
        return medias.get(id);
    }

}
