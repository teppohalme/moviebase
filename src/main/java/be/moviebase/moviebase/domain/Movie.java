package be.moviebase.moviebase.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Column(nullable = false)
    private String title;

    @Column(nullable = true)
    private String director;

    @Min(value = 1800, message = "Release year seems too early")
    @Column(nullable = true)
    private Integer releaseYear;

    @Min(1)
    @Max(5)
    @Column(nullable = true)
    private double rating;

    @Column(nullable = true)
    private String posterUrl;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    public Movie() {}

    public Movie(String title, String director, Integer releaseYear, double rating, String posterUrl) {
        this.title = title;
        this.director = director;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.posterUrl = posterUrl;
    }

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }

    public Integer getReleaseYear() { return releaseYear; }
    public void setReleaseYear(Integer releaseYear) { this.releaseYear = releaseYear; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public String getPosterUrl() { return posterUrl; }
    public void setPosterUrl(String posterUrl) { this.posterUrl = posterUrl; }

    public List<Review> getReviews() { return reviews; }
    public void setReviews(List<Review> reviews) { this.reviews = reviews; }

    public void addReview(Review review) {
        reviews.add(review);
        review.setMovie(this);
    }

    public void removeReview(Review review) {
        reviews.remove(review);
        review.setMovie(null);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", director='" + director + '\'' +
                ", releaseYear=" + releaseYear +
                ", rating=" + rating +
                ", posterUrl='" + posterUrl + '\'' +
                '}';
    }
}
