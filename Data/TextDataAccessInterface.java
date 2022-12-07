package Data;

import java.util.List;

public interface TextDataAccessInterface {
    
    List<String> load(String path);

    void Write(List<String> input, String path); 

}