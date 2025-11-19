package ir.maktabsharif.cinemawatchingsystem.service;

import ir.maktabsharif.cinemawatchingsystem.model.Movie;
import ir.maktabsharif.cinemawatchingsystem.model.User;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public interface UserService extends BaseService<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    void signup(String username, String password, String email);

    User login(String username, String password);

    User editUser(User user, String username, String email, String currentPassword,
                  String newPassword, String confirmPassword, String base64Image);

    void addMovieToWatchlist(User user, Movie movie);

    void removeMovieFromWatchlist(User user, Movie movie);
}
