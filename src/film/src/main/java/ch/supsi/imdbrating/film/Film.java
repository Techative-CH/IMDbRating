package ch.supsi.imdbrating.film;

public class Film {
    private String posterLink;
    private String title;
    private int releaseYear;
    private String certificate;
    private int duration;
    private String[] genres;
    private float imdbRating;
    private String overview;
    private int metaScore;
    private String director;
    private String[] star;
    private String numberOfVotes;
    private String gross;

    public Film(String posterLink, String title, int releaseYear, String certificate, int duration, String[] genres, float imdbRating, String overview, int metaScore, String director, String[] star, String numberOfVotes, String gross) {
        this.posterLink = posterLink;
        this.title = title;
        this.releaseYear = releaseYear;
        this.certificate = certificate;
        this.duration = duration;
        this.genres = genres;
        this.imdbRating = imdbRating;
        this.overview = overview;
        this.metaScore = metaScore;
        this.director = director;
        this.star = star;
        this.numberOfVotes = numberOfVotes;
        this.gross = gross;
    }

    public String getPosterLink() {
        return posterLink;
    }

    public String getTitle() {
        return title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getCertificate() {
        return certificate;
    }

    public int getDuration() {
        return duration;
    }

    public String[] getGenres() {
        return genres;
    }

    public float getImdbRating() {
        return imdbRating;
    }

    public String getOverview() {
        return overview;
    }

    public int getMetaScore() {
        return metaScore;
    }

    public String getDirector() {
        return director;
    }

    public String[] getStar() {
        return star;
    }

    public String getNumberOfVotes() {
        return numberOfVotes;
    }

    public String getGross() {
        return gross;
    }
}
