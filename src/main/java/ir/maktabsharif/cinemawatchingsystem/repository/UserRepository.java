package ir.maktabsharif.cinemawatchingsystem.repository;

import ir.maktabsharif.cinemawatchingsystem.model.User;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}


