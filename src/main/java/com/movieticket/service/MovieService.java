package com.movieticket.service;

import com.movieticket.entity.Movie;
import com.movieticket.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(String id) { // Change parameter type to String
        return movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found"));
    }

    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie updateMovie(String id, Movie movie) { // Change parameter type to String
        Movie existingMovie = getMovieById(id);
        existingMovie.setTitle(movie.getTitle());
        existingMovie.setGenre(movie.getGenre());
        existingMovie.setDirector(movie.getDirector());
        existingMovie.setReleaseDate(movie.getReleaseDate());
        existingMovie.setDuration(movie.getDuration());
        existingMovie.setPricePerTicket(movie.getPricePerTicket());
        existingMovie.setDescription(movie.getDescription());
        existingMovie.setRating(movie.getRating());
        existingMovie.setAvailableShowtimes(movie.getAvailableShowtimes());
        return movieRepository.save(existingMovie);
    }

    public void deleteMovie(String id) { // Change parameter type to String
        movieRepository.deleteById(id);
    }
}