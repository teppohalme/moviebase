package be.moviebase.moviebase.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class TmdbSearchResponse {

    private List<MovieResult> results;

    public List<MovieResult> getResults() {
        return results;
    }

    public void setResults(List<MovieResult> results) {
        this.results = results;
    }

    public static class MovieResult {

        private Integer id;
        private String title;
        private String overview;

        @JsonProperty("release_date")
        private String releaseDate;

        @JsonProperty("vote_average")
        private Double rating;

        @JsonProperty("poster_path")
        private String posterPath;

        public Integer getId() { return id; }
        public String getTitle() { return title; }
        public String getOverview() { return overview; }
        public String getReleaseDate() { return releaseDate; }
        public Double getRating() { return rating; }
        public String getPosterPath() { return posterPath; }

        public String getReleaseYear() {
            return (releaseDate != null && releaseDate.length() >= 4)
                    ? releaseDate.substring(0, 4)
                    : "Unknown";
        }
    }
}
