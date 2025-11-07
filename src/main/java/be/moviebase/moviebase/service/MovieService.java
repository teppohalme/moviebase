package be.moviebase.moviebase.service;

import be.moviebase.moviebase.dto.TmdbSearchResponse;
import be.moviebase.moviebase.dto.TmdbMovieDetailsResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class MovieService {

    @Value("${tmdb.api.key}")
    private String apiKey;

    @Value("${tmdb.api.base}")
    private String baseUrl;

    private final RestTemplate restTemplate;

    public MovieService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public TmdbSearchResponse searchMovies(String query) {
        String url = UriComponentsBuilder
                .fromUriString(baseUrl + "/search/movie")
                .queryParam("api_key", apiKey)
                .queryParam("query", query)
                .toUriString();

        return restTemplate.getForObject(url, TmdbSearchResponse.class);
    }

    public TmdbMovieDetailsResponse getMovieDetails(Integer tmdbId) {
        String url = UriComponentsBuilder
                .fromUriString(baseUrl + "/movie/" + tmdbId)
                .queryParam("api_key", apiKey)
                .toUriString();

        return restTemplate.getForObject(url, TmdbMovieDetailsResponse.class);
    }
}
