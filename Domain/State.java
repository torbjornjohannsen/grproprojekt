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

        numFormat = NumberFormat.getInstance(Locale.FRANCE); 
    }

    public Boolean init() {
        TextDataAccessInterface tLoader = new TextDataAccess(); 
        PictureDataAccessInterface pLoader = new PictureDataAccess(); 

        Boolean moviesRes = InitMediaType(tLoader.load("film"), pLoader);
        Boolean seriesRes = InitMediaType(tLoader.load("serier"), pLoader);
        Boolean usersRes = initUsers(tLoader.load("users"));    

        
        for(Media m : medias) {
            String[] titleWords = cleanSearchString(m.title);
            for(String s : titleWords) {
                //if(s.isEmpty()) { break; } // fucking jesus
                if(titleWordMap.containsKey(s)) { // faster than checking the list since its sorted
                    titleWordMap.get(s).add(m); 
                } else {
                    titleWordList.add(s); 

                    List<Media> mList = new ArrayList<>(); 
                    mList.add(m); 

                    titleWordMap.put(s, mList); 
                }
            }
            
            String[] years = m.getYear().trim().split("-"); 
            
            for(String s : years) {
                s = s.trim();
                int year = Integer.parseInt(s); 
                if(yearMap.containsKey(year)) {
                    yearMap.get(year).add(m); 
                } else {
                    List<Media> mList = new ArrayList<>(); 
                    mList.add(m);
                    yearMap.put(year, mList);
                }
            }

            

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

        Collections.sort(titleWordList);

        /* System.out.println("Sorted title list: "); 
        for(String s : titleWordList) {
            System.out.println("\"" + s + "\": ");
            for(Media m : titleWordMap.get(s)) {
                System.out.println("  - "+ getMediaInformation(m.id));
            }
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
        //ArrayList<Media> results = new ArrayList<>();
        Set<Media> resSet = new HashSet<>(); 
        /* for(Media media : medias) {
            if(media.getTitle().toLowerCase().contains(input.toLowerCase()) || 
        input.toLowerCase().contains(media.getTitle().toLowerCase()) || 
        input.contains(media.getStartYear())) {
                results.add(media);
            }
        } */
        /* input = input.toUpperCase(); // for standardization
        input = input.trim(); // since we use spaces to signify ever */
        String[] words = cleanSearchString(input); 
        List<String> titleMatches = new ArrayList<>();
        List<Integer> yearMatches = new ArrayList<>();
        for(String s : words ) {
            List<String> match; 
            if(s.matches("\\d+")) {
                yearMatches.add(Integer.parseInt(s.trim())); 
            } else {
                match = containsSearch(s, titleWordList); 
                if(match == null) { continue; }
                titleMatches.addAll(match);
            }
            
        }
        if(titleMatches.size() + yearMatches.size() == 0) { return new ArrayList<Media>(); } // empty list
        

        Collections.sort(titleMatches); 

        for(String s : titleMatches) {
            //System.out.println("dumb cunt: " + s); 
            for (Media m : titleWordMap.get(s)) {
                resSet.add(m);
            }
        }

        for (Integer i : yearMatches) {
            for(Media m : yearMap.get(i)) {
                resSet.add(m);
            }
        }

        return new ArrayList<Media>(resSet); 
    }

    private List<String> containsSearch(String input, List<String> prevList) {
        int midIndex = prevList.size() / 2; 
        //System.out.println("midIndex: " + midIndex); 
        if(prevList.size() < 2) { return null; }
        String midVal = prevList.get(midIndex); 
        //System.out.println(midIndex + ": cmp: \"" + input + "\" and \"" + midVal + "\"");
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

    private String[] cleanSearchString(String in) {
        in = " " + in + " "; // to make sure all seperate words have spaces in front and back 
        in = in.toUpperCase(); 
        in = in.replaceAll(" ", "  "); // double space so we can remove double invalids
        // Remove common words with no real meaning
        in = in.replaceAll(" THE | A | I | AN | YOU | OF | AND | IN | TO | WE |'S| IT'S | IT |,|\\.|-| ALL |&|;", " ");
        in = in.trim(); 
        String[] out = in.split(" +"); 
        for(String s : out) {
            s = s.trim();
            if(s.isEmpty()) { throw new InputMismatchException("invalid input: " + s + " in cleanSearchString"); }
        }
        return out; 
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

            users.add(new User(fields[0], Integer.parseInt(fields[1].trim()), fields[2].trim()));

            String[] watched = fields[3].split(","); 
            if(watched.length > 1) { // split just returns the same string if no matches, and its concievable that a user has no watched or favorites
                for(String w : watched) {
                    users.get(users.size() - 1).AddWHistory(Integer.parseInt(w.trim()));
                }
            }

            String[] favorites = fields[4].split(","); 
            if(favorites.length > 1) {
                for(String f : favorites) {
                    users.get(users.size() - 1).AddFavorite(Integer.parseInt(f.trim()));
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
