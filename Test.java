import Domain.*;
import Data.*; 

import java.awt.image.*;
import java.util.*;

public class Test {
    public static void main(String[] args) {
        TestPictureDataAccess();
        TestTextDataAccess(); 
        TestState();
    }

    private static void TestTextDataAccess() {
        System.out.println("TestTextDataAcess: ");
        TextDataAccessInterface loader = new TextDataAccess(); 

        List<String> res = loader.load("film"); 

        System.out.println("Film list size: " + res.size());

        loader.write(res.subList(0, 10), "test");
        System.out.println("");

    }

    private static void TestPictureDataAccess() {
        System.out.println("TestPictureDataAcess: ");
        PictureDataAccessInterface loader = new PictureDataAccess(); 

        // Valid file name, supposed to just load it 
        BufferedImage validImage = loader.load("24"); 
        // Invalid file name, supposed to say its invalid then load the default file 
        BufferedImage invalidImage = loader.load("invalid file name"); 

        System.out.println("Valid image dimensions: " + validImage.getWidth() + ", " + validImage.getHeight());
        System.out.println("Invalid image dimensions: " + invalidImage.getWidth() + ", " + invalidImage.getHeight());
        System.out.println("");
    }

    private static void TestState() {
        System.out.println("TestState: ");

        StateInterface state = new State(); 

        state.init();

        List<? extends Displayable> list1 = state.getDisplayables();

        System.out.println("Size: " + list1.size());

        List<? extends Displayable> list2 = state.getGenreList("Biography");
        
        //Expecting drama media
        for (Displayable element : list2) {
            System.out.println(state.getMediaInformation(element.getId()));
        }
        
        List<? extends Displayable> list3 = state.search("2017");

        //Expecting media from 2017
        for (Displayable element : list3) {
            System.out.println(state.getMediaInformation(element.getId()));
        }
        
        // expected to work fine
        state.addFavorite(5);
        state.addFavorite(69, 0);
        state.addWatched(5);
        state.addWatched(69, 0);

        // expected to fail
        state.addFavorite(2, -5);
        state.addWatched(2, -5);

        state.writeUsers();

        state.search("lord rings");
        System.out.println("");

    }

    //lav en fjerde test hvor en ny tekstfil indeholder cursed serier, der skal give korrekt error handling

}
