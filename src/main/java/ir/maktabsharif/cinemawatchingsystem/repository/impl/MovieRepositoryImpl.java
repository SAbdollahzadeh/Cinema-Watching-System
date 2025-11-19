package ir.maktabsharif.cinemawatchingsystem.repository.impl;

import ir.maktabsharif.cinemawatchingsystem.model.Movie;
import ir.maktabsharif.cinemawatchingsystem.repository.MovieRepository;

public class MovieRepositoryImpl extends BaseRepositoryImpl<Movie, Long> implements MovieRepository {

    public MovieRepositoryImpl() {
        super(Movie.class);
    }

    @Override
    public Class<Movie> getModelClass() {
        return Movie.class;
    }
}
