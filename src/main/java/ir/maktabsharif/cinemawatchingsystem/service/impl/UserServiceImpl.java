package ir.maktabsharif.cinemawatchingsystem.service.impl;

import ir.maktabsharif.cinemawatchingsystem.enums.UserLevel;
import ir.maktabsharif.cinemawatchingsystem.exceptions.*;
import ir.maktabsharif.cinemawatchingsystem.model.Movie;
import ir.maktabsharif.cinemawatchingsystem.model.RegularUser;
import ir.maktabsharif.cinemawatchingsystem.model.User;
import ir.maktabsharif.cinemawatchingsystem.service.UserService;
import ir.maktabsharif.cinemawatchingsystem.util.EntityManagerProvider;
import ir.maktabsharif.cinemawatchingsystem.repository.UserRepository;
import ir.maktabsharif.cinemawatchingsystem.util.PasswordUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

import java.io.InputStream;
import java.util.Base64;
import java.util.Optional;

public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {

    UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        em = EntityManagerProvider.getEntityManager();
        userRepository.setRepositoryEntityManager(em);
        try {
            return userRepository.findByUsername(username);
        } catch (Exception e) {
            return Optional.empty();
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        em = EntityManagerProvider.getEntityManager();
        userRepository.setRepositoryEntityManager(em);
        try {
            return userRepository.findByEmail(email);
        } catch (Exception e) {
            return Optional.empty();
        } finally {
            em.close();
        }

    }

    @Override
    public boolean existsByUsername(String username) {
        em = EntityManagerProvider.getEntityManager();
        userRepository.setRepositoryEntityManager(em);
        try {
            return userRepository.existsByUsername(username);
        } finally {
            em.close();
        }
    }


    @Override
    public boolean existsByEmail(String email) {
        em = EntityManagerProvider.getEntityManager();
        userRepository.setRepositoryEntityManager(em);
        try {
            return userRepository.existsByEmail(email);
        } finally {
            em.close();
        }
    }

    @Override
    public void signup(String username, String password, String email) {
        if (username.trim().isEmpty() ||
                password.isEmpty() || email.trim().isEmpty()) {
            throw new InvalidRegisterationInputsException("Username, password and email shouldn't be empty");
        }

        em = EntityManagerProvider.getEntityManager();
        userRepository.setRepositoryEntityManager(em);
        try {
            if (existsByUsername(username.trim())) {
                throw new DuplicateUsernameException("Username is already taken");
            }
            if (existsByEmail(email.trim())) {
                throw new DuplicateEmailException("Email is already taken");
            }

            RegularUser regularUser = RegularUser.builder()
                    .username(username.trim())
                    .email(email.trim())
                    .password(PasswordUtil.hashPassword(password))
                    .userLevel(UserLevel.USER_LEVEL_1)
                    .build();
            try {
                save(regularUser);
            } catch (Exception e) {
                throw new RuntimeException("Server Error while saving user" + e.getMessage());
            }
        } finally {
            em.close();
        }

    }

    @Override
    public User login(String username, String password) {
        User user = findByUsername(username)
                .orElseThrow(() -> new InvalidUsernameException("Username was not found"));
        if (!PasswordUtil.checkPassword(password, user.getPassword())) {
            throw new InvalidPasswordException("Password is incorrect");
        }
        return user;
    }

    @Override
    public User editUser(User user, String username, String email, String currentPassword, String newPassword, String confirmPassword, String base64Image) {
        if (username != null && !username.trim().isEmpty() &&
                email != null && !email.trim().isEmpty()) {

            user.setUsername(username);
            user.setEmail(email);
            user.setProfilePicture(base64Image);

            if (currentPassword != null && !currentPassword.trim().isEmpty() &&
                    newPassword != null && !newPassword.trim().isEmpty() &&
                    confirmPassword != null && !confirmPassword.trim().isEmpty()) {

                if (!PasswordUtil.checkPassword(currentPassword, user.getPassword())) {
                    throw new UserUpdateException("Password is incorrect");
                }

                if (!confirmPassword.equals(newPassword)) {
                    throw new UserUpdateException("New Password doesn't match confirm password!");
                }
                user.setPassword(PasswordUtil.hashPassword(newPassword));
            }

            try {
                return update(user);
            }catch (Exception e) {
                throw new UserUpdateException("Error while updating user profile");
            }
        }

        return user;
    }

    @Override
    public void addMovieToWatchlist(User user, Movie movie) {
            if (((RegularUser) user).getMovies().add(movie)) {
                try {
                    update(user);
                } catch (Exception e) {
//                    throw new UserUpdateException("Error while adding movie to watchlist");
                    throw new UserUpdateException(e.getMessage());
                }
            } else{
                throw new UserUpdateException("Movie already exists in Watchlist");
            }
    }

    @Override
    public void removeMovieFromWatchlist(User user, Movie movie) {
        if (((RegularUser) user).getMovies().remove(movie)) {
            try {
                update(user);
            } catch (Exception e) {
                throw new UserUpdateException("Error while removing movie to watchlist");
            }
        } else{
            throw new UserUpdateException("Movie dose not exist in Watchlist");
        }
    }

    @Override
    public Optional<User> findByUser(User user) {
        if (user == null)
            return Optional.empty();

        if (user.getId() != null) {
            return findById(user.getId());
        } else if (user.getUsername() != null) {
            return findByUsername(user.getUsername());
        } else {
            return Optional.empty();
        }
    }
}
