package Data;

import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class PictureDataAccess implements PictureDataAccessInterface {
    public PictureDataAccess(String folderPath) {
        folder = "./MedieData/" + folderPath + "/";
    }
    private String folder; 

    public BufferedImage load(String imageName){
        String path = folder + imageName + ".jpg";
        BufferedImage image; 

        try {
            image = ImageIO.read(new File(path));
        }  catch (IOException e) {
            try {

                System.out.println("PictureDataAccess error: " + e.getMessage() + " For image: \"" +  imageName + "\"");
                image = ImageIO.read(new File("./MedieData/default.png"));
            } catch (IOException ex) {
                System.out.println("PictureDataAccess error: " + e.getMessage() + " For default");
                return null; // really shouldnt ever happen but java autism freaks out if we dont do this since ImageIO.read throws an exception
            }
        }
        return image;
    }
}
