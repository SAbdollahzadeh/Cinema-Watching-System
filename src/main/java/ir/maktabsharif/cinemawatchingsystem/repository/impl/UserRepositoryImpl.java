package ir.maktabsharif.cinemawatchingsystem.repository.impl;

import ir.maktabsharif.cinemawatchingsystem.model.User;
import ir.maktabsharif.cinemawatchingsystem.repository.UserRepository;
import jakarta.persistence.NoResultException;

import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl extends BaseRepositoryImpl<User, Long> implements UserRepository {

    public UserRepositoryImpl() {
        super(User.class);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(
                em.createQuery("select u from User u where u.username =: username", getModelClass())
                        .setParameter("username", username)
                        .getSingleResult());
    }

    public Optional<User> findByEmail(String email) {
        try {
            return Optional.ofNullable(
                    em.createQuery("select u from User u where u.email = :email", getModelClass())
                            .setParameter("email", email)
                            .getSingleResult()
            );
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean existsByUsername(String username) {
        return em.createQuery("select count(u) from User u where u.username =: username  ", Long.class)
                .setParameter("username", username)
                .getSingleResult() > 0;
    }

    @Override
    public boolean existsByEmail(String email) {
        return em.createQuery("select count(u) from User u where u.email =: email  ", Long.class)
                .setParameter("email", email)
                .getSingleResult() > 0;
    }

    @Override
    public Class<User> getModelClass() {
        return User.class;
    }
}

