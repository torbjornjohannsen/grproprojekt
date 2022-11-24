package Data;
import java.awt.image.*;
import java.io.IOException;

public interface PictureDataAccessInterface {
    public BufferedImage Load(String imageName) throws IOException; 
}
