package ch.supsi.imdbrating.film;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

public abstract class FilmStatistics {
    private static List<Film> films = new ArrayList<>();

    private static Map<String, Object> readStatistics() {
        Map<String, Object> stats = new HashMap<>();

        int numberOfFilms = films.size();

        stats.put("TotalNumberOfFilms", numberOfFilms);

        double avgMovieRuntime = films.stream()
                .mapToInt(Film::getDuration)
                .average()
                .orElse(0);

        stats.put("AverageMovieRuntime", avgMovieRuntime);

        String bestDirector = films.stream()
                .collect(Collectors.groupingBy(
                        Film::getDirector,
                        Collectors.averagingDouble(Film::getImdbRating)
                ))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Nessun direttore");

        stats.put("BestDirector", bestDirector);

        String mostPresentActor = films.stream()
                .flatMap(film -> Arrays.stream(film.getStar()))
                .collect(Collectors.groupingBy(
                        actor -> actor,
                        Collectors.counting()
                ))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Attore non trovato");

        stats.put("MostPresentActor", mostPresentActor);

        int mostProductiveYear = films.stream()
                .collect(Collectors.groupingBy(
                        Film::getReleaseYear,
                        Collectors.counting()
                ))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(-1);

        stats.put("MostProductiveYear", mostProductiveYear);

        return stats;
    }

    private static boolean createFolderIfNotExists(File file) {
        return file.exists() || file.mkdirs();
    }

    private static void writeFile(String output, Map<String, Object> stats) throws IOException {
        StringBuilder sb = new StringBuilder();
        File f = new File(output);
        File parentFolder = f.getParentFile();

        if (!createFolderIfNotExists(parentFolder)) {
            throw new IOException("Film statistics folders cannot be created");
        }

        stats.forEach((key, value) -> {
            sb.append(key).append("=").append(value).append('\n');
        });

        Files.writeString(Paths.get(f.toURI()), sb.toString(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    private static void readFile(String input) throws IOException{
        boolean isFirstLine = true;

        try(BufferedReader br = new BufferedReader(new FileReader(input))){
            String line;

            while((line = br.readLine()) != null) {
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
            }
        }
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
            Map<String, Object> stats = readStatistics();
            writeFile(output, stats);
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
    }
}
