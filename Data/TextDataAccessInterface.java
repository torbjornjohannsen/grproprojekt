package Data;

import java.util.List;

public interface TextDataAccessInterface {
    List<String> load(String path);
    void write(List<String> input, String path); 
}