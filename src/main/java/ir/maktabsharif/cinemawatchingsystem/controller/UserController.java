package ir.maktabsharif.cinemawatchingsystem.controller;

import ir.maktabsharif.cinemawatchingsystem.exceptions.UserUpdateException;
import ir.maktabsharif.cinemawatchingsystem.model.Movie;
import ir.maktabsharif.cinemawatchingsystem.model.RegularUser;
import ir.maktabsharif.cinemawatchingsystem.model.User;
import ir.maktabsharif.cinemawatchingsystem.repository.impl.MovieRepositoryImpl;
import ir.maktabsharif.cinemawatchingsystem.repository.impl.UserRepositoryImpl;
import ir.maktabsharif.cinemawatchingsystem.service.MovieService;
import ir.maktabsharif.cinemawatchingsystem.service.UserService;
import ir.maktabsharif.cinemawatchingsystem.service.impl.MovieServiceImpl;
import ir.maktabsharif.cinemawatchingsystem.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.InputStream;
import java.util.Base64;
import java.util.List;

public class UserController implements Controller {
    @Override
    public String handle(HttpServletRequest req, HttpServletResponse resp) {
        String pathInfo = req.getPathInfo();
        return switch (pathInfo) {
            case ("/user/login") -> login(req);
            case ("/user/signup") -> signup(req);
            case ("/user/logout") -> logout(req);
            case ("/user/edit") -> edit(req);
            case ("/user/watchlist") -> watchlist(req);
            case ("/user/add-watchlist-movie") -> addMovieToWatchlist(req);
            case ("/user/remove-watchlist-movie") -> removeMovieFromWatchlist(req);
            default -> index(req);
        };
    }

    private String watchlist(HttpServletRequest req) {
        UserService userService = new UserServiceImpl(new UserRepositoryImpl());
        User user = userService.findById(
                ((User) req.getSession().getAttribute("user")).getId()
        ).orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (user instanceof RegularUser regularUser) {
            if (req.getSession().getAttribute("message") != null) {
                req.setAttribute("message", req.getSession().getAttribute("message"));
                req.setAttribute("messageType", req.getSession().getAttribute("messageType"));
                req.getSession().removeAttribute("message");
                req.getSession().removeAttribute("messageType");
            }
            req.setAttribute("movies", regularUser.getMovies());
            return "/jsp/user/watchlist";
        } else {
            // todo this part shoud be 404 not access
            return "redirect:/app/user/index";
        }
    }

    private String removeMovieFromWatchlist(HttpServletRequest req) {
        UserService userService = new UserServiceImpl(new UserRepositoryImpl());
        MovieService movieService = new MovieServiceImpl(new MovieRepositoryImpl());
        Movie movie = movieService.findById(Long.parseLong(req.getParameter("id"))).orElseThrow(() -> new UserUpdateException("Movie not found in remove process"));
        User user = (User) req.getSession().getAttribute("user");
        try {
            if (user != null && user instanceof RegularUser) {
                userService.removeMovieFromWatchlist(user, movie);
                req.getSession().setAttribute("message", "Movie was removed to watchlist");
                req.getSession().setAttribute("messageType", "success");
            } else {
                throw new UserUpdateException("User is not a regular user or was not found");
            }
        } catch (Exception e) {
            req.getSession().setAttribute("message", e.getMessage());
            req.getSession().setAttribute("messageType", "error");
        }
        return "redirect:/app/user/watchlist";
    }

    private String addMovieToWatchlist(HttpServletRequest req) {
        UserService userService = new UserServiceImpl(new UserRepositoryImpl());
        MovieService movieService = new MovieServiceImpl(new MovieRepositoryImpl());
        Movie movie = movieService.findById(Long.parseLong(req.getParameter("id"))).get();
        User user = (User) req.getSession().getAttribute("user");

        try {
            if (user != null && user instanceof RegularUser) {
                userService.addMovieToWatchlist(user, movie);
                req.getSession().setAttribute("message", "Movie added to watchlist");
                req.getSession().setAttribute("messageType", "success");
            } else {
                throw new UserUpdateException("User is not a regular user or was not found");
            }
        } catch (Exception e) {
            req.getSession().setAttribute("message", e.getMessage());
            req.getSession().setAttribute("messageType", "error");
        }
        return "redirect:/app/user/index";
    }

    private String edit(HttpServletRequest req) {
        switch (req.getMethod()) {
            case "GET":
                return "/jsp/user/edit";
            case "POST": {
                UserService userService = new UserServiceImpl(new UserRepositoryImpl());
                String username = req.getParameter("username");
                String email = req.getParameter("email");
                String currentPassword = req.getParameter("currentPassword");
                String newPassword = req.getParameter("newPassword");
                String confirmPassword = req.getParameter("confirmPassword");

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

                try {
                    User user = (User) req.getSession().getAttribute("user");
                    if (user == null) {
                        throw new UserUpdateException("User not found");
                    }
                    user = userService.editUser(user, username, email, currentPassword, newPassword, confirmPassword, base64Image);
                    req.getSession().setAttribute("user", user);
                    req.getSession().setAttribute("message", "User edited successfully");
                    req.getSession().setAttribute("messageType", "success");
                } catch (Exception e) {
                    req.getSession().setAttribute("message", e.getMessage());
                    req.getSession().setAttribute("messageType", "error");
                }
                return "redirect:/app/user/index";
            }
            default:
                return "/jsp/user/index";
        }
    }

    private String logout(HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.invalidate();
        return "redirect:/app/index";
    }

    private String index(HttpServletRequest req) {
        if (req.getSession().getAttribute("message") != null) {
            req.setAttribute("message", req.getSession().getAttribute("message"));
            req.setAttribute("messageType", req.getSession().getAttribute("messageType"));
            req.getSession().removeAttribute("message");
            req.getSession().removeAttribute("messageType");
        }
        MovieService movieService = new MovieServiceImpl(new MovieRepositoryImpl());
        List<Movie> movies = movieService.findAll();
        req.setAttribute("movies", movies);
        return "jsp/user/index";
    }

    private String signup(HttpServletRequest req) {
        String method = req.getMethod();
        switch (method) {
            case "GET":
                return "jsp/user/signup";
            case "POST":
                String username = req.getParameter("username");
                String password = req.getParameter("password");
                String email = req.getParameter("email");
                try {
                    UserService userService = new UserServiceImpl(new UserRepositoryImpl());
                    userService.signup(username, password, email);
                } catch (Exception e) {
                    req.setAttribute("message", e.getMessage());
                    req.setAttribute("messageType", "error");
                    return "jsp/user/signup";
                }
                req.setAttribute("message", "Signup Successful");
                req.setAttribute("messageType", "success");
                return "jsp/user/login";
//                return "redirect:app/user/login";
            case "PUT":
                break;
            case "DELETE":
                break;
            default:
        }
        return "jsp/user/register"; // toDelete
    }

    private String login(HttpServletRequest req) {
        String method = req.getMethod();
        switch (method) {
            case "GET":
                return "jsp/user/login";
            case "POST":
                String username = req.getParameter("username");
                String password = req.getParameter("password");
                String rememberMe = req.getParameter("remember-me");
                UserService userService = new UserServiceImpl(new UserRepositoryImpl());
                try {
                    User user = userService.login(username, password);
                    req.getSession().setAttribute("user", user);
                    // TODO this part should be handle using tokens and cookie
                    if (rememberMe != null) {
                        // todo
                    }
                    return "redirect:/app/user/index";
                } catch (Exception e) {
                    req.setAttribute("message", e.getMessage());
                    req.setAttribute("messageType", "error");
                    return "jsp/user/login";
                }
            case "PUT":
                break;
            case "DELETE":
                break;
            default:
        }
        return "jsp/user/login";
    }
}
