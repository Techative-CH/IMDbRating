package ch.supsi.imdbrating.film;

public class Main {
    public static void main(String[] args) {
        //dopo va messo i controlli
        System.out.println(args[0]);
        FilmStatistics.run(args[0], args[1]);
    }
}