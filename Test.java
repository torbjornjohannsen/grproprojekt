import Domain.*;
import Data.*; 

import java.awt.image.*;
import java.util.*;


public class Test {
    public static void main(String[] args) {
        Boolean ultResult = true; 
        ultResult = ultResult && testPictureDataAccess();
        ultResult = ultResult && testTextDataAccess(); 
        ultResult = ultResult && testMedia();
        ultResult = ultResult && testSearch();
        ultResult = ultResult && testUser();
        System.out.println("So final test result: " + ultResult);

        TestInvalidFile(); // This kills the program
        System.out.println("This should never be reached");
    }

    private static Boolean testTextDataAccess() {
        Boolean result = true; 
        System.out.println("testTextDataAcess: ");
        TextDataAccessInterface loader = new TextDataAccess(); 

        List<String> res = loader.load("film"); 

        result = res.size() == 100; 
        System.out.println("Loaded film list size: " + result);

        loader.write(res, "test");
        return result;
    }

    private static Boolean testPictureDataAccess() {
        Boolean result = true, temp; 
        System.out.println("testPictureDataAcess: ");
        PictureDataAccessInterface sLoader = new PictureDataAccess("serieforsider"); 

        // Valid file name, supposed to just load it 
        BufferedImage validImage = sLoader.load("24"); 
        // Invalid file name, supposed to say its invalid then load the default file 
        BufferedImage invalidImage = sLoader.load("invalid file name"); 

        temp = validImage.getWidth() == 140 && validImage.getHeight() == 209; 
        result = result && temp; 
        System.out.println("Valid image dimensions: " + temp);
        temp = invalidImage.getWidth() == 140 && invalidImage.getHeight() == 210; 
        result = result && temp; 
        System.out.println("Invalid image dimensions: " + temp);

        System.out.println("Final result: " + result + "\n\n");
        return result; 
    }

    private static Boolean testMedia() {
        System.out.println("testMedia: ");
        Boolean allTests = true, temp;

        StateInterface state = new State(); 
        state.init();

        List<? extends Displayable> list1 = state.getDisplayables();
        temp = list1.size() == 199; 
        allTests = allTests && temp; 
        System.out.println("Size of all Displayable: " + temp);

        List<? extends Displayable> list2 = state.getSeriesDisplayables();
        temp = list2.size() == 99; 
        allTests = allTests && temp; 
        System.out.println("Size of all Series Displayables: " + temp);

        List<? extends Displayable> list3 = state.getMovieDisplayables();
        temp = list3.size() == 100; 
        allTests = allTests && temp; 
        System.out.println("Size of all Movies Displayables: " + temp);

        System.out.println("Final result: " + allTests + "\n\n");
        return allTests; 
    }

    private static Boolean testSearch() {
        System.out.println("testSearch: ");

        StateInterface state = new State(); 
        state.init();
        Boolean allTests = true, temp; 

        System.out.println("Valid genre test:");
        try {
            List<? extends Displayable> list1 = state.getGenreList("Biography");

            List<String> bioTitles = new ArrayList<>();
            bioTitles.add("I Claudius"); 
            bioTitles.add("Spartacus"); 
        
            temp = list1.size() == bioTitles.size(); 
            allTests = temp && allTests; 
            System.out.println("Correct size of result list: " + temp);

            //Expecting biography media
            for (int i = 0; i < bioTitles.size(); i++) {
                temp = bioTitles.get(i).equals(list1.get(i).getTitle()); 
                System.out.println("Case: " + i + ": " + temp);
                allTests = allTests && temp; 
            }
    
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        System.out.println("Invalid genre search:");
        try {
            List<? extends Displayable> list2 = state.getGenreList("qwH ewEEøøæ ee HNNq ");
            temp =  (list2 == null); 
            allTests = allTests && temp;
            //Expecting no media
            System.out.println("Nonexistant genre returns empty list: " + temp);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        System.out.println("Valid year search: ");
        try {
            List<? extends Displayable> list3 = state.search("1951");

            //Expecting media from 2017
            for (Displayable element : list3) {
                temp = element.getYear().contains("1951"); 
                System.out.println("Correct year: " +  temp);
                allTests = allTests && temp; 
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        System.out.println("Valid title search:");
        try {
            List<? extends Displayable> list4 = state.search("lord rings sopranos");

            List<String> searchRes = new ArrayList<>();
            searchRes.add("Lord Of The Rings - The Return Of The King"); 
            searchRes.add("The Sopranos"); 

            temp = list4.size() == searchRes.size(); 
            allTests = temp && allTests; 
            System.out.println("Correct size of result list: " + temp);

            //Expecting no media
            for (int i = 0; i < searchRes.size(); i++) {
                temp = searchRes.get(i).equals(list4.get(i).getTitle()); 
                allTests = temp && allTests; 
                System.out.println("Title match: " + temp);
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        
    
        
        System.out.println("All search test results: " + allTests + "\n\n");
        return allTests; 
    }

    private static Boolean testUser() {
        System.out.println("testUser: ");
        Boolean result = true, temp; 

        StateInterface state = new State(); 
        state.init();
        
        state.addUser("testman", 100, "testgender", true);
        

        UserInterface testUser = state.getUser(); 
        temp = testUser.getAge() == 100 && testUser.getGender().equals("testgender") && testUser.getName().equals("testman"); 
        result = result && temp; 
        System.out.println("User adding: " + temp);

        int testUsrID = state.getCurUser(); 

        // expected to work fine
        state.addFavorite(5);
        state.addFavorite(69, testUsrID);

        List<Integer> favList = new ArrayList<>(testUser.getFavoriteList()); 

        List<Integer> favTList = new ArrayList<>(); 
        favTList.add(5); 
        favTList.add(69);

        temp = favList.size() == favTList.size(); 
        result = result && temp; 
        System.out.println("favoriteList size: " + temp);

        for(int i = 0; i < favList.size(); i++) {
            temp = favList.get(i) == favTList.get(i); 
            result = result && temp; 
            System.out.println("Favorites: " + i + " " + temp);
        }

        // expected to fail
        state.addFavorite(2, -5);

         

        // We explictly dont write the users to the file so we can test the adding user functionality each time
        //state.writeUsers();
        System.out.println("Final result: " + result + "\n\n");
        return result; 
    }
    
    private static void TestInvalidFile() {
        TextDataAccessInterface t = new TextDataAccess();
        
        t.load("tototototototqoweq");
    }
}
