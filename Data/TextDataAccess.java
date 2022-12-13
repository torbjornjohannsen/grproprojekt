package Data;

import java.io.*;
import java.util.*;


public class TextDataAccess implements Data.TextDataAccessInterface {

    public List<String> load(String path) {
        List<String> dataSepereatedIntoStrings = new ArrayList<String>();
        File inputFile = new File("./MedieData/" + path + ".txt");
        
        try {
            Scanner sc = new Scanner(inputFile, "UTF-8");

            while(sc.hasNextLine()) {
                dataSepereatedIntoStrings.add(sc.nextLine());
            }

            sc.close();

        } catch(Exception fnfe) {
            System.out.println(fnfe.getMessage());
        }

        return dataSepereatedIntoStrings;
    }

    public void Write(List<String> input, String path) {

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
