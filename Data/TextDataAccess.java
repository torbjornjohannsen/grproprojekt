package Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TextDataAccess implements Data.TextDataAccessInterface {

    public List<String> load(String path) {
        List<String> dataSepereatedIntoStrings = new ArrayList<String>();
        File inputFile = new File("./MedieData/" + path + ".txt");
        
        try {
            Scanner sc = new Scanner(inputFile);

            while(sc.hasNextLine()) {
                dataSepereatedIntoStrings.add(sc.nextLine());
            }

            sc.close();

        } catch(FileNotFoundException fnfe) {
            System.out.println(fnfe.getMessage());
        }

        return dataSepereatedIntoStrings;
    }

}
