package be.moviebase.moviebase.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import be.moviebase.moviebase.domain.Movie;
import be.moviebase.moviebase.service.MovieService;
import be.moviebase.moviebase.dto.TmdbSearchResponse;
import be.moviebase.moviebase.repository.MovieRepository;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieRepository repository;
    private final MovieService movieService;

    public MovieController(MovieRepository repository, MovieService movieService) {
        this.repository = repository;
        this.movieService = movieService;
    }

   // search movies from the API
    @GetMapping("/search")
    public TmdbSearchResponse searchMovies(@RequestParam String query) {
        return movieService.searchMovies(query);
    }

   // get all moveies from local DB
    @GetMapping
    public List<Movie> getAllMovies() {
        return repository.findAll();
    }

    // get movie by id
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // save movie to local DB
    @PostMapping
    public Movie createMovie(@RequestBody Movie movie) {
        return repository.save(movie);
    }

   @PostMapping("/save/{tmdbId}")
    public ResponseEntity<Movie> saveMovieFromApi(@PathVariable Integer tmdbId) {
    var movieData = movieService.getMovieDetails(tmdbId);

    Movie movie = new Movie();
    movie.setTitle(movieData.getTitle());

    // get release year
    String date = movieData.getReleaseDate();
    int year = (date != null && date.length() >= 4)
            ? Integer.parseInt(date.substring(0, 4))
            : 0; // fallback if no release date

    movie.setReleaseYear(year);

    // rating from API
    Double tmdbRating = movieData.getRating();
    int scaledRating = (tmdbRating != null)
            ? (int) Math.round(tmdbRating / 2)
            : 0;

    movie.setRating(scaledRating);

    Movie saved = repository.save(movie);
    return ResponseEntity.ok(saved);
}


    // update movie to local DB
    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie updatedMovie) {
        return repository.findById(id)
                .map(movie -> {
                    movie.setTitle(updatedMovie.getTitle());
                    movie.setDirector(updatedMovie.getDirector());
                    movie.setReleaseYear(updatedMovie.getReleaseYear());
                    movie.setRating(updatedMovie.getRating());
                    return ResponseEntity.ok(repository.save(movie));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // delete movie from local DB
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
