package Data;

import java.io.*;
import java.util.*;

public class TextDataAccess implements Data.TextDataAccessInterface {

    public List<String> load(String path) {
        List<String> dataSepereatedIntoStrings = new ArrayList<String>();
        File inputFile = new File("./MedieData/" + path + ".txt");
        
        
        try {
            // From https://stackoverflow.com/a/27473547
            Scanner sc = new Scanner(inputFile, "ISO-8859-1");

            while(sc.hasNextLine()) {
                dataSepereatedIntoStrings.add(sc.nextLine());
            }
            
            sc.close();

        } catch(FileNotFoundException fnfe) {
            System.out.println(fnfe.getMessage());
            System.exit(-1);
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
