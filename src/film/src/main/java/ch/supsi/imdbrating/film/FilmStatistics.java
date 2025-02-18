package ch.supsi.imdbrating.film;
//import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public abstract class FilmStatistics {
    private static Film[] films;

    private static void writeFile(String output){
        //...
    }

    private static void readFile(String input) throws IOException{
        try(BufferedReader br = new BufferedReader(new FileReader(input))){
            String line;
            while((line = br.readLine()) != null) {
                System.out.println(line);
                //film = new Film(...);
            }
        }
    }

    public static void run(String input, String output) {
        try {
            readFile(input);
            writeFile(output);
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
    }
}
