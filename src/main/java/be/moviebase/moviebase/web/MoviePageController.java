package be.moviebase.moviebase.web;

import be.moviebase.moviebase.domain.Movie;
import be.moviebase.moviebase.domain.Review;
import be.moviebase.moviebase.dto.TmdbMovieDetailsResponse;
import be.moviebase.moviebase.repository.MovieRepository;
import be.moviebase.moviebase.repository.ReviewRepository;
import be.moviebase.moviebase.service.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class MoviePageController {

    private final MovieService movieService;
    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;

    public MoviePageController(MovieService movieService,
                               MovieRepository movieRepository,
                               ReviewRepository reviewRepository) {
        this.movieService = movieService;
        this.movieRepository = movieRepository;
        this.reviewRepository = reviewRepository;
    }

    // home page
    @GetMapping("/")
    public String home(@RequestParam(required = false) String query, Model model) {
        if (query != null && !query.isEmpty()) {
            var searchResponse = movieService.searchMovies(query);
            model.addAttribute("movies", searchResponse.getResults());
        }
        return "home";
    }

    // my movies page
    @GetMapping("/my-movies")
    public String myMovies(Model model) {
        List<Movie> myMovies = movieRepository.findAll();
        model.addAttribute("myMovies", myMovies);
        model.addAttribute("newReview", new Review()); // ✅ Needed for the form binding
        return "mymovies";
    }

    // add movie to local db
    @PostMapping("/save-from-api/{tmdbId}")
    public String saveFromApi(@PathVariable Integer tmdbId, RedirectAttributes redirectAttributes) {
        TmdbMovieDetailsResponse movieData = movieService.getMovieDetails(tmdbId);

        Movie movie = new Movie();
        movie.setTitle(movieData.getTitle());
        movie.setDirector("Unknown");
        movie.setReleaseYear(movieData.getReleaseYear());
        movie.setPosterUrl(movieData.getPosterPath());

        Double tmdbRating = movieData.getRating();
        int scaledRating = (tmdbRating != null)
                ? (int) Math.round(tmdbRating / 2)
                : 0;
        movie.setRating(scaledRating);

        movieRepository.save(movie);
        redirectAttributes.addFlashAttribute("successMessage", "✅ Added to My Movies successfully!");
        return "redirect:/";
    }

    // delete movie
    @PostMapping("/delete-movie/{id}")
    public String deleteMovie(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        movieRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Movie removed successfully!");
        return "redirect:/my-movies";
    }

    // add a review
    @PostMapping("/movies/{movieId}/reviews")
    public String addReview(@PathVariable Long movieId, @ModelAttribute Review review, RedirectAttributes redirectAttributes) {
        Movie movie = movieRepository.findById(movieId).orElseThrow();
        review.setMovie(movie);
        reviewRepository.save(review);
        redirectAttributes.addFlashAttribute("successMessage", "✅ Review added successfully!");
        return "redirect:/my-movies";
    }
}
