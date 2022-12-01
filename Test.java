import Domain.*;
import Data.*; 

import java.io.File;
import java.awt.image.*;
import java.util.*;

public class Test {
    public static void main(String[] args) {
        TestPictureDataAccess(); 
        TestTextDataAccess(); 
        TestState();
    }

    // Temporary tests, need to do it with JUnit properly later but just for now this works fine
    private static void TestTextDataAccess() {
        TextDataAccessInterface loader = new TextDataAccess(); 

        List<String> res = loader.load("film"); 

        System.out.println("Film list size: " + res.size());
    }

    private static void TestPictureDataAccess() {
        PictureDataAccessInterface loader = new PictureDataAccess(); 

        // Valid file name, supposed to just load it 
        BufferedImage validImage = loader.Load("24"); 
        // Invalid file name, supposed to say its invalid then load the default file 
        BufferedImage invalidImage = loader.Load("qwewqeqweq invalid file name xdddddddddddd dd"); 

        System.out.println("Valid image dimensions: " + validImage.getWidth() + ", " + validImage.getHeight());
        System.out.println("Invalid image dimensions: " + invalidImage.getWidth() + ", " + invalidImage.getHeight());

    }

    private static void TestState() {
        StateInterface state = new Domain.State(); 

        state.init();


        for(int i = 0; i < 200; i++) {
            System.out.println(i + " " + state.getMediaInformation(i));
        }


        // expected to work fine
        state.AddFavorite(5);
        state.AddFavorite(69, 0);

        // expected to fail
        state.AddFavorite(2, -5);

    }
}
