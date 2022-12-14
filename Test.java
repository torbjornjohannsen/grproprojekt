import Domain.*;
import Data.*; 

import java.awt.image.*;
import java.util.*;


public class Test {
    public static void main(String[] args) {
        testPictureDataAccess();
        testTextDataAccess(); 
        testMedia();
        testSearch();
        testUser();
    }

    private static void testTextDataAccess() {
        System.out.println("testTextDataAcess: ");
        TextDataAccessInterface loader = new TextDataAccess(); 

        List<String> res = loader.load("test"); 

        System.out.println("Film list size: " + res.size());

        loader.write(res, "test");
        System.out.println((int)"ä".charAt(0) + ":" + (int)res.get(0).charAt(0));

    }

    private static void testPictureDataAccess() {
        System.out.println("testPictureDataAcess: ");
        PictureDataAccessInterface sLoader = new PictureDataAccess("pictures"); 

        // Valid file name, supposed to just load it 
        BufferedImage validImage = sLoader.load("24"); 
        // Invalid file name, supposed to say its invalid then load the default file 
        BufferedImage invalidImage = sLoader.load("invalid file name"); 

        System.out.println("Valid image dimensions: " + validImage.getWidth() + ", " + validImage.getHeight());
        System.out.println("Invalid image dimensions: " + invalidImage.getWidth() + ", " + invalidImage.getHeight());
        System.out.println("");
    }

    private static void testMedia() {
        System.out.println("testMedia: ");

        StateInterface state = new State(); 
        state.init();

        List<? extends Displayable> list1 = state.getDisplayables();
        System.out.println("Size: " + list1.size());

        List<? extends Displayable> list2 = state.getSeriesDisplayables();
        System.out.println("Size: " + list2.size());

        List<? extends Displayable> list3 = state.getMovieDisplayables();
        System.out.println("Size: " + list3.size());

        System.out.println("");
    }

    private static void testSearch() {
        System.out.println("testSearch: ");

        StateInterface state = new State(); 
        state.init();

        try {
            List<? extends Displayable> list1 = state.getGenreList("Biography");
        
            //Expecting biography media
            for (Displayable element : list1) {
                System.out.println(element.getTitle());
            }
    
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        
        try {
            List<? extends Displayable> list2 = state.getGenreList("Silent film");

            //Expecting no media
            for (Displayable element : list2) {
                System.out.println(element.getTitle());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        try {
            List<? extends Displayable> list3 = state.search("1951");

            //Expecting media from 2017
            for (Displayable element : list3) {
                System.out.println(element.getTitle());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        

        try {
            List<? extends Displayable> list4 = state.search("lord rings");

            //Expecting no media
            for (Displayable element : list4) {
                System.out.println(element.getTitle());
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        
    
        
        System.out.println("");

    }

    private static void testUser() {
        System.out.println("testUser: ");

        StateInterface state = new State(); 
        state.init();
        
        // expected to work fine
        state.addFavorite(5);
        state.addFavorite(69, 0);
        state.addWatched(5);
        state.addWatched(69, 0);

        // expected to fail
        state.addFavorite(2, -5);
        state.addWatched(2, -5);

        state.writeUsers();
        System.out.println("");
    }

    //lav en fjerde test hvor en ny tekstfil indeholder cursed serier, der skal give korrekt error handling

}
