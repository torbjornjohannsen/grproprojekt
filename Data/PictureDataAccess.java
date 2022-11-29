package Data;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;


public class PictureDataAccess implements PictureDataAccessInterface {

    public BufferedImage Load(String imageName){
        String path = "./MedieData/pictures/" + imageName + ".jpg";
        BufferedImage image; 
        try {
            image = ImageIO.read(new File(path));
        }  catch (IOException e) {

            try {

                System.out.println(e.getMessage() + " For image: \"" +  imageName + "\"");
                image = ImageIO.read(new File("./MedieData/pictures/default.png"));
            } catch (IOException ex) {
                System.out.println(e.getMessage() + " For default");
                return null; // really shouldnt ever happen but java autism freaks out if we dont do this since ImageIO.read throws an exception
            }

        }
        return image;

    }
}
