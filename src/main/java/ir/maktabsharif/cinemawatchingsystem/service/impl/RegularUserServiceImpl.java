package ir.maktabsharif.cinemawatchingsystem.service.impl;

import ir.maktabsharif.cinemawatchingsystem.model.Movie;
import ir.maktabsharif.cinemawatchingsystem.model.RegularUser;
import ir.maktabsharif.cinemawatchingsystem.service.RegularUserService;
import ir.maktabsharif.cinemawatchingsystem.util.EntityManagerProvider;
import ir.maktabsharif.cinemawatchingsystem.repository.MovieRepository;
import ir.maktabsharif.cinemawatchingsystem.repository.UserRepository;
import jakarta.persistence.EntityManager;

public class RegularUserServiceImpl implements RegularUserService {
    UserRepository userRepository;
    MovieRepository movieRepository;

    public RegularUserServiceImpl(UserRepository userRepository, MovieRepository movieRepository) {
        this.userRepository = userRepository;
        this.movieRepository = movieRepository; // todo should be delete?
    }

    @Override
    public void addMovieToWatchlist(RegularUser regularUser, Movie movie) {
        EntityManager em = EntityManagerProvider.getEntityManager();

        if (regularUser.getMovies().add(movie)) {
            commit(regularUser, em);
        }
    }

    @Override
    public void removeMovieFromWatchlist(RegularUser regularUser, Movie movie) {
        EntityManager em = EntityManagerProvider.getEntityManager();
        if (regularUser.getMovies().remove(movie)) {
            commit(regularUser, em);
        }

    }

    private void commit(RegularUser regularUser, EntityManager em) {
        try {
            userRepository.setRepositoryEntityManager(em);
            em.getTransaction().begin();
            userRepository.update(regularUser);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            if (em.isOpen())
                em.close();
        }
    }
}
