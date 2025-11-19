package ir.maktabsharif.cinemawatchingsystem.service;

import ir.maktabsharif.cinemawatchingsystem.model.Movie;
import ir.maktabsharif.cinemawatchingsystem.model.RegularUser;

public interface RegularUserService {
    void addMovieToWatchlist(RegularUser regularUser, Movie movie);
    void removeMovieFromWatchlist(RegularUser regularUser, Movie movie);
}
