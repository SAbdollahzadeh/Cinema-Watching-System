package ir.maktabsharif;

import ir.maktabsharif.cinemawatchingsystem.model.User;
import ir.maktabsharif.cinemawatchingsystem.repository.impl.UserRepositoryImpl;
import ir.maktabsharif.cinemawatchingsystem.util.EntityManagerProvider;
import jakarta.persistence.EntityManager;

import java.util.List;

public class Main2 {
    public static void main(String[] args) {
        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        EntityManager em = EntityManagerProvider.getEntityManager();
        userRepository.setRepositoryEntityManager(em);
        List<User> users= userRepository.findAll();




        var a = 10;
    }
}
