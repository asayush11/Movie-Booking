package src;

public class Movie {
    private final String name;
    private final String genre;
    private final String language;

    public Movie(String name, String genre, String language) {
        this.name = name;
        this.genre = genre;
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public String getLanguage() {
        return language;
    }
}
