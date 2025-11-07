package be.moviebase.moviebase.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MovieResponse {

    private String title;

    @JsonProperty("release_date")
    private String releaseDate;

    private String director; 

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("vote_average")
    private Double rating;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getReleaseDate() { return releaseDate; }
    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }

    public String getPosterPath() { return posterPath; }
    public void setPosterPath(String posterPath) { this.posterPath = posterPath; }

    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }
}
