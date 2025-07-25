package com.example.moviedb;

import java.io.Serializable;

public class Movie implements Serializable {
    private String title;
    private String year;
    private String imdbID;
    private String poster;
    private String imdbRating;
    private String country;
    private String plot;
    private String genre;
    private String director;
    private String rated;
    private String released;
    private String runtime;
    private String writer;
    private String actors;
    private String language;
    private String awards;
    private String boxOffice;

    // Constructor
    public Movie(String title, String year, String imdbID, String poster, String imdbRating, String country,
                 String plot, String genre, String director, String rated, String released, String runtime,
                 String writer, String actors, String language, String awards, String boxOffice) {
        this.title = title;
        this.year = year;
        this.imdbID = imdbID;
        this.poster = poster;
        this.imdbRating = imdbRating;
        this.country = country;
        this.plot = plot;
        this.genre = genre;
        this.director = director;
        this.rated = rated;
        this.released = released;
        this.runtime = runtime;
        this.writer = writer;
        this.actors = actors;
        this.language = language;
        this.awards = awards;
        this.boxOffice = boxOffice;
    }


    // Getters and setters for all fields
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getYear() { return year; }
    public void setYear(String year) { this.year = year; }

    public String getImdbID() { return imdbID; }
    public void setImdbID(String imdbID) { this.imdbID = imdbID; }

    public String getPoster() { return poster; }
    public void setPoster(String poster) { this.poster = poster; }

    public String getImdbRating() { return imdbRating; }
    public void setImdbRating(String imdbRating) { this.imdbRating = imdbRating; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getPlot() { return plot; }
    public void setPlot(String plot) { this.plot = plot; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }

    public String getRated() { return rated; }
    public void setRated(String rated) { this.rated = rated; }

    public String getReleased() { return released; }
    public void setReleased(String released) { this.released = released; }

    public String getRuntime() { return runtime; }
    public void setRuntime(String runtime) { this.runtime = runtime; }

    public String getWriter() { return writer; }
    public void setWriter(String writer) { this.writer = writer; }

    public String getActors() { return actors; }
    public void setActors(String actors) { this.actors = actors; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public String getAwards() { return awards; }
    public void setAwards(String awards) { this.awards = awards; }

    public String getBoxOffice() { return boxOffice; }
    public void setBoxOffice(String boxOffice) { this.boxOffice = boxOffice; }
}
