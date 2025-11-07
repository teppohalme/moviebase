package be.moviebase.moviebase.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import be.moviebase.moviebase.domain.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByTitleContainingIgnoreCase(String title);
}
