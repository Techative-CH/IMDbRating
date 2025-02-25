package ch.supsi.imdbrating.film;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public abstract class FilmStatistics {
    private static List<Film> films = new ArrayList<>();

    private static void writeFile(String output) {
        int numberOfFilms = films.size();

        System.out.println("Total number of movies: " + numberOfFilms);

        Double avgMovieRuntime = films.stream().mapToInt(Film::getDuration).average().orElse(0);

        System.out.println("Average movies run-time: " + avgMovieRuntime);

        String bestDirector = films.stream()
                .collect(Collectors.groupingBy(
                        Film::getDirector,
                        Collectors.averagingDouble(Film::getImdbRating)
                ))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Nessun direttore");

        System.out.println("Best director: " + bestDirector);

        String actorPresence = films.stream()
                .flatMap(film -> Arrays.stream(film.getStar()))
                .collect(Collectors.groupingBy(actor -> actor, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Attore non trovato");

        System.out.println("Most present actor/actress: " + actorPresence);
    }

    private static void readFile(String input) throws IOException{
        boolean isFirstLine = true;
        try(BufferedReader br = new BufferedReader(new FileReader(input))){
            String line;
            while((line = br.readLine()) != null) {
                System.out.println(line);
                if(isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                List<String> columns = readCSV(line);

                films.add(new Film(
                        columns.get(0),
                        columns.get(1),
                        columns.get(2).isBlank() ? 0 : Integer.parseInt(columns.get(2)),
                        columns.get(3),
                        columns.get(4).isBlank() ? 0 : Integer.parseInt(columns.get(4).split(" ")[0]),
                        columns.get(5).split(", "),
                        columns.get(6).isBlank() ? 0f : Float.parseFloat(columns.get(6)),
                        columns.get(7),
                        columns.get(8).isBlank() ? 0 : Integer.parseInt(columns.get(8)),
                        columns.get(9),
                        new String[]{
                                columns.get(10),
                                columns.get(11),
                                columns.get(12),
                                columns.get(13)
                        },
                        columns.get(14).isBlank() ? 0 : Long.parseLong(columns.get(14)),
                        columns.get(15)
                ));
                System.out.println(films.get(films.size()-1));
            }
        }
        //films.forEach(System.out::println);
    }

    private static List<String> readCSV(String line){
        List<String> result = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;

        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                result.add(sb.toString().trim());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        result.add(sb.toString().trim());
        return result;
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
