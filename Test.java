import Data.PictureDataAccess;
import Data.PictureDataAccessInterface;
import java.io.File;
import java.awt.image.*;

public class Test {
    public static void main(String[] args) {
        TestPictureDataAccess(); 
        TestTextDataAccess(); 
    }

    // Temporary tests, need to do it with JUnit properly later but just for now this works fine
    private static void TestTextDataAccess() {
        System.out.println("Not implemented");
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
}
