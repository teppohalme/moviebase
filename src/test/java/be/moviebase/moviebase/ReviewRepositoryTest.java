package be.moviebase.moviebase;

import be.moviebase.moviebase.domain.Movie;
import be.moviebase.moviebase.domain.Review;
import be.moviebase.moviebase.repository.MovieRepository;
import be.moviebase.moviebase.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void testSaveAndFindByMovieId() {
        // Create and save a movie
        Movie movie = new Movie();
        movie.setTitle("There Will Be Blood");
        movie.setDirector("Paul Thomas Anderson");
        movie.setReleaseYear(2007);
        movie.setRating(5);
        movieRepository.save(movie);

        // Create and save a review
        Review review = new Review();
        review.setComment("Daniel Day-Lewis 5/5");
        review.setRating(5);
        review.setMovie(movie);
        reviewRepository.save(review);

        // Fetch reviews by movie ID
        List<Review> reviews = reviewRepository.findByMovieId(movie.getId());

        // Verify
        assertThat(reviews).isNotEmpty();
        assertThat(reviews.get(0).getComment()).isEqualTo("Daniel Day-Lewis 5/5");
        assertThat(reviews.get(0).getMovie().getTitle()).isEqualTo("There Will Be Blood");
    }
}
