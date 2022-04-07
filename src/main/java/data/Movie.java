package data;

import java.io.Serializable;

public class Movie implements Serializable {
    private int id;
    private String title;
    private String director;
    private String poster;
    private String dateReleased;
    private int yearMade;
    private String genre;
    private String plot;
    private String rating;
    private double imdb;
    private int runtime;
    private String actors;

    public Movie() {

    }

    public Movie(int id, String title, String director, String poster, String dateReleased, int yearMade, String genre, String plot, String rating, double imdb, int runtime, String actors) {
        this.id = id;
        this.title = title;
        this.director = director;
        this.poster = poster;
        this.dateReleased = dateReleased;
        this.yearMade = yearMade;
        this.genre = genre;
        this.plot = plot;
        this.rating = rating;
        this.imdb = imdb;
        this.runtime = runtime;
        this.actors = actors;
    }

    @Override
    public String toString() {
        return String.format("%s - Title, %d - yearMade",title, yearMade);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getDateReleased() {
        return dateReleased;
    }

    public void setDateReleased(String dateReleased) {
        this.dateReleased = dateReleased;
    }

    public int getYearMade() {
        return yearMade;
    }

    public void setYearMade(int yearMade) {
        this.yearMade = yearMade;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public double getImdb() {
        return imdb;
    }

    public void setImdb(double imdb) {
        this.imdb = imdb;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }
}
