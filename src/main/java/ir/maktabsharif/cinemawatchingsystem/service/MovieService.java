package ir.maktabsharif.cinemawatchingsystem.service;

import ir.maktabsharif.cinemawatchingsystem.model.Movie;
import jakarta.servlet.http.HttpServletRequest;

public interface MovieService extends BaseService<Movie, Long> {
    boolean deleteMovieById(Long id);
    boolean addMovie(HttpServletRequest req);

    boolean editMovie(HttpServletRequest req);
}
