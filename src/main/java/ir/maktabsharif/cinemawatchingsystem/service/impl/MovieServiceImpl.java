package ir.maktabsharif.cinemawatchingsystem.service.impl;

import ir.maktabsharif.cinemawatchingsystem.enums.Genre;
import ir.maktabsharif.cinemawatchingsystem.exceptions.DeletingMovieException;
import ir.maktabsharif.cinemawatchingsystem.exceptions.MovieEditingException;
import ir.maktabsharif.cinemawatchingsystem.exceptions.MovieSavingException;
import ir.maktabsharif.cinemawatchingsystem.model.Movie;
import ir.maktabsharif.cinemawatchingsystem.repository.MovieRepository;
import ir.maktabsharif.cinemawatchingsystem.service.MovieService;
import ir.maktabsharif.cinemawatchingsystem.util.EntityManagerProvider;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.Base64;

public class MovieServiceImpl extends BaseServiceImpl<Movie, Long> implements MovieService {
    MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        super(movieRepository);
        this.movieRepository = movieRepository;
    }

    @Override
    public boolean deleteMovieById(Long id) {
        try {
            EntityManager em = EntityManagerProvider.getEntityManager();
            movieRepository.setRepositoryEntityManager(em);
            Movie movie = movieRepository.findById(id)
                    .orElseThrow(() -> new DeletingMovieException("Movie not found! "));
            delete(movie);
            return true;
        } catch (RuntimeException e) {
            throw new DeletingMovieException("Fail in deleting movie");
        } finally {
            em.close();
        }
    }

    @Override
    public boolean addMovie(HttpServletRequest req) {
        String title = req.getParameter("title");
        Genre genre = Genre.valueOf(req.getParameter("genre"));
        LocalDate releaseDate = LocalDate.parse(req.getParameter("releaseDate"));
        String description = req.getParameter("description");

        String base64Image = null;
        try {
            Part filePart = req.getPart("image");

            if (filePart != null && filePart.getSize() > 0) {
                try (InputStream inputStream = filePart.getInputStream()) {
                    byte[] imageBytes = inputStream.readAllBytes();
                    base64Image = Base64.getEncoder().encodeToString(imageBytes);
                }
            }
        } catch (Exception e) {
            req.getServletContext().log("Error reading image", e);
        }

        Movie movie = Movie.builder()
                .title(title)
                .releaseDate(releaseDate)
                .description(description)
                .picture(base64Image)
                .genre(genre)
                .build();
        try {
            save(movie);
        } catch (RuntimeException e) {
            throw new MovieSavingException("Fail in saving movie");
        }
        return true;
    }

    @Override
    public boolean editMovie(HttpServletRequest req) {
        String title = req.getParameter("title");
        Genre genre = Genre.valueOf(req.getParameter("genre"));
        LocalDate releaseDate = LocalDate.parse(req.getParameter("releaseDate"));
        String description = req.getParameter("description");
        Long id = Long.parseLong(req.getParameter("movie-id"));

        String base64Image = null;
        try {
            Part filePart = req.getPart("image");

            if (filePart != null && filePart.getSize() > 0) {
                try (InputStream inputStream = filePart.getInputStream()) {
                    byte[] imageBytes = inputStream.readAllBytes();
                    base64Image = Base64.getEncoder().encodeToString(imageBytes);
                }
            }
        } catch (Exception e) {
            req.getServletContext().log("Error reading image", e);
        }

        Movie movie = findById(id).orElseThrow(() -> new MovieEditingException("Movie with id:" + id + "was not found."));
        movie.setTitle(title);
        movie.setReleaseDate(releaseDate);
        movie.setDescription(description);
        movie.setGenre(genre);
        movie.setPicture(base64Image);

        try {
            update(movie);
        } catch (RuntimeException e) {
            throw new MovieEditingException("Fail in editing movie");
        }
        return true;
    }
}
