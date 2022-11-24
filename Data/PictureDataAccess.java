package Data;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;


public class PictureDataAccess implements PictureDataAccessInterface {
    public BufferedImage Load(String imageName) {
        String path = "MedieData/pictures/" + imageName + ".jpg";
        
        try {
            return ImageIO.read(new File(path));
        }  catch (IOException e) {
            return ImageIO.read(new File("MedieData/Pictures/default.jpg"))
        }
        

    }
}
