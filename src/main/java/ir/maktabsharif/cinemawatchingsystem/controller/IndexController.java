package ir.maktabsharif.cinemawatchingsystem.controller;

import ir.maktabsharif.cinemawatchingsystem.model.Movie;
import ir.maktabsharif.cinemawatchingsystem.repository.impl.MovieRepositoryImpl;
import ir.maktabsharif.cinemawatchingsystem.service.MovieService;
import ir.maktabsharif.cinemawatchingsystem.service.impl.MovieServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class IndexController implements Controller{
    @Override
    public String handle(HttpServletRequest req, HttpServletResponse resp) {
        return index(req);
    }

    private String index(HttpServletRequest req) {
        MovieService  movieService = new MovieServiceImpl(new MovieRepositoryImpl());
        List<Movie> movies = movieService.findAll();
        req.setAttribute("movies", movies);
        return "jsp/index";
    }
}
