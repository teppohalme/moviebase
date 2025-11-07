package be.moviebase.moviebase.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TmdbMovieDetailsResponse {

    private Integer id;
    private String title;
    private String overview;

    @JsonProperty("release_date")
    private String releaseDate;

    @JsonProperty("vote_average")
    private Double rating;

    @JsonProperty("poster_path")
    private String posterPath;

    private String director;

    public Integer getId() { return id; }
    public String getTitle() { return title; }
    public String getOverview() { return overview; }
    public String getReleaseDate() { return releaseDate; }
    public Double getRating() { return rating; }
    public String getPosterPath() { return posterPath; }
    public String getDirector() { return director; }

    public void setId(Integer id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setOverview(String overview) { this.overview = overview; }
    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }
    public void setRating(Double rating) { this.rating = rating; }
    public void setPosterPath(String posterPath) { this.posterPath = posterPath; }
    public void setDirector(String director) { this.director = director; }

    public Integer getReleaseYear() {
        return (releaseDate != null && releaseDate.length() >= 4)
                ? Integer.parseInt(releaseDate.substring(0, 4))
                : null;
    }

    public String getPosterUrl() {
        if (posterPath == null || posterPath.isEmpty()) {
            return "/images/no-poster-available.png"; // mikäli posteria ei löydy, tämä palauttaa tyhjän kuvan errorin sijaan
        }
        return "https://image.tmdb.org/t/p/w500" + posterPath;
    }
}
