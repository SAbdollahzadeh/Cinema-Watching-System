package ir.maktabsharif;

import ir.maktabsharif.cinemawatchingsystem.enums.Genre;
import ir.maktabsharif.cinemawatchingsystem.enums.UserLevel;
import ir.maktabsharif.cinemawatchingsystem.model.Comment;
import ir.maktabsharif.cinemawatchingsystem.model.Movie;
import ir.maktabsharif.cinemawatchingsystem.model.RegularUser;
import ir.maktabsharif.cinemawatchingsystem.repository.impl.CommentRepositoryImpl;
import ir.maktabsharif.cinemawatchingsystem.repository.impl.MovieRepositoryImpl;
import ir.maktabsharif.cinemawatchingsystem.repository.impl.UserRepositoryImpl;
import ir.maktabsharif.cinemawatchingsystem.service.CommentService;
import ir.maktabsharif.cinemawatchingsystem.service.MovieService;
import ir.maktabsharif.cinemawatchingsystem.service.RegularUserService;
import ir.maktabsharif.cinemawatchingsystem.service.UserService;
import ir.maktabsharif.cinemawatchingsystem.service.impl.CommentServiceImpl;
import ir.maktabsharif.cinemawatchingsystem.service.impl.MovieServiceImpl;
import ir.maktabsharif.cinemawatchingsystem.service.impl.RegularUserServiceImpl;
import ir.maktabsharif.cinemawatchingsystem.service.impl.UserServiceImpl;
import ir.maktabsharif.cinemawatchingsystem.util.PasswordUtil;

import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl(new UserRepositoryImpl());
        MovieService movieService = new MovieServiceImpl(new MovieRepositoryImpl());

        RegularUserService regularUserService = new RegularUserServiceImpl(new UserRepositoryImpl(), new MovieRepositoryImpl());

        Movie movie = Movie.builder()
                .title("t1")
                .genre(Genre.ACTION)
                .duration(120.2)
                .comments(new HashSet<>())
                .build();
        Movie movie2 = Movie.builder()
                .title("t2")
                .genre(Genre.ACTION)
                .duration(120.2)
                .comments(new HashSet<>())
                .build();

        movieService.save(movie);
        movieService.save(movie2);

        RegularUser regularUser = RegularUser.builder()
                .username("jane_doe")
                .email("jane@example.com")
                .password(PasswordUtil.hashPassword("123"))
                .userLevel(UserLevel.USER_LEVEL_1)
                .movies(new HashSet<>())
                .comments(new HashSet<>())
                .build();

        RegularUser regularUser2 = RegularUser.builder()
                .username("jane_doe1")
                .email("jane@example.com")
                .password(PasswordUtil.hashPassword("123"))
                .userLevel(UserLevel.USER_LEVEL_1)
                .movies(new HashSet<>())
                .comments(new HashSet<>())
                .build();

        System.out.println(regularUser.equals(regularUser2));

        RegularUser user = (RegularUser)userService.save(regularUser);
        regularUserService.addMovieToWatchlist(user, movie);



        Comment comment = Comment.builder()
                .comment("test comment")
                .movie(movie)
                .user(user)
                .build();

        Comment comment2 = Comment.builder()
                .comment("test comment2")
                .movie(movie2)
                .user(user)
                .build();

        CommentService commentService = new CommentServiceImpl(new CommentRepositoryImpl());

        commentService.save(comment);
        commentService.save(comment2);

        System.out.println(comment2.equals(comment));

//        RegularUser regularUser3 = (RegularUser) userService.findById(1L).get();
//
//        commentService.delete(comment);
//
//        movieService.delete(movie);
    }
}
