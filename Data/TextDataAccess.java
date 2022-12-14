package Data;

import java.io.*;
import java.util.*;
import java.util.stream.*;

public class TextDataAccess implements Data.TextDataAccessInterface {

    public List<String> load(String path) {
        List<String> dataSepereatedIntoStrings = new ArrayList<String>();
        File inputFile = new File("./MedieData/" + path + ".txt");
        
        
        try {
            // From https://stackoverflow.com/a/9282017 
            BufferedReader bufRdr  = new BufferedReader(
            new InputStreamReader(new FileInputStream(inputFile), "UTF-8"));
            String line;

            while((line = bufRdr.readLine()) != null) {
                dataSepereatedIntoStrings.add(line);
            }
            
            bufRdr.close();

        } catch(Exception fnfe) {
            System.out.println(fnfe.getMessage());
        }

        return dataSepereatedIntoStrings;
    }

    public void write(List<String> input, String path) {
        try {
            FileWriter outputFile = new FileWriter("./MedieData/" + path + ".txt"); 
            for (String string : input) {
                outputFile.write(string + "\n"); 
            }
            outputFile.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
