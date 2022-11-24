package Domain;

import java.awt.image.BufferedImage;
import java.util.List;

public interface StateInterface {
    public Boolean init(List<String> media);
    public String getMediaInformation(int id);
    public BufferedImage getMediaPicture(int id);
}
