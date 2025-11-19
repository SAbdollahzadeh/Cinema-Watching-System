package ir.maktabsharif.cinemawatchingsystem.controller;

import ir.maktabsharif.cinemawatchingsystem.enums.Genre;
import ir.maktabsharif.cinemawatchingsystem.exceptions.InvalidMovieException;
import ir.maktabsharif.cinemawatchingsystem.model.Movie;
import ir.maktabsharif.cinemawatchingsystem.repository.impl.MovieRepositoryImpl;
import ir.maktabsharif.cinemawatchingsystem.service.MovieService;
import ir.maktabsharif.cinemawatchingsystem.service.impl.MovieServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MovieController implements Controller {
    @Override
    public String handle(HttpServletRequest req, HttpServletResponse resp) {
        String pathInfo = req.getPathInfo();
        if (pathInfo.startsWith("/movie/add")) {
            return add(req);
        } else if (pathInfo.startsWith("/movie/delete")) {
            return delete(req);
        } else if (pathInfo.startsWith("/movie/edit")) {
            return edit(req);
        } else {
            return "jsp/user/index";
        }
    }

    private String edit(HttpServletRequest req) {
        MovieService movieService = new MovieServiceImpl(new MovieRepositoryImpl());
        switch (req.getMethod()) {
            case "GET":
                if (req.getParameter("id") != null) {
                    try {
                        Movie movie =
                                movieService.findById(Long.parseLong(req.getParameter("id")))
                                        .orElseThrow(() -> new InvalidMovieException("Movie with this id was not found"));
                        req.setAttribute("movie", movie);
                    } catch (Exception e) {
                        req.getSession().setAttribute("message", e.getMessage());
                        req.getSession().setAttribute("messageType", "error");
                        return "redirect:/app/user/index";
                    }
                }
                return "jsp/movie/add-edite";
            case "POST":
                try {
                    movieService.editMovie(req);
                    req.getSession().setAttribute("message", "Movie edited");
                    req.getSession().setAttribute("messageType", "success");
                } catch (Exception e) {
                    req.getSession().setAttribute("message", "Movie not edited");
                    req.getSession().setAttribute("messageType", "error");
                }
                return "redirect:/app/user/index";
            default:
                return "redirect:/app/user/index";
        }

    }

    private String add(HttpServletRequest req) {
        MovieService movieService = new MovieServiceImpl(new MovieRepositoryImpl());
        switch (req.getMethod()) {
            case ("GET"):
                return "jsp/movie/add-edite";
            case ("POST"):
                try {
                    movieService.addMovie(req);
                    req.getSession().setAttribute("message", "Movie added");
                    req.getSession().setAttribute("messageType", "success");
                } catch (Exception e) {
                    req.getSession().setAttribute("message", "Movie not added");
                    req.getSession().setAttribute("messageType", "error");
                }
                return "redirect:/app/user/index";
            default:
                return "redirect:/app/user/index";
        }
    }

    private String delete(HttpServletRequest req) {
        MovieService movieService = new MovieServiceImpl(new MovieRepositoryImpl());
        String movieIdString = req.getParameter("id");
        if (movieIdString == null || movieIdString.trim().isEmpty()) {
            req.getSession().setAttribute("message", "No movie selected");
            req.getSession().setAttribute("messageType", "error");

        } else {
            Long movieId = Long.parseLong(movieIdString);
            try {
                movieService.deleteMovieById(movieId);
                req.getSession().setAttribute("message", "Deleting Successfully");
                req.getSession().setAttribute("messageType", "success");
            } catch (Exception e) {
                req.getSession().setAttribute("message", e.getMessage());
                req.getSession().setAttribute("messageType", "error");
            }
        }
        return "redirect:/app/user/index";
    }
}
