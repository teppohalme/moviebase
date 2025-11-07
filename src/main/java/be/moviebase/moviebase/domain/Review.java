package be.moviebase.moviebase.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @Min(1)
    @Max(5)
    @Column(nullable = false)
    private int rating;

    @Size(max = 500)
    private String comment;

    public Review() {}

    public Review(Movie movie, int rating, String comment) {
        this.movie = movie;
        this.rating = rating;
        this.comment = comment;
    }

    public Long getId() { return id; }
    public Movie getMovie() { return movie; }
    public void setMovie(Movie movie) { this.movie = movie; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}
