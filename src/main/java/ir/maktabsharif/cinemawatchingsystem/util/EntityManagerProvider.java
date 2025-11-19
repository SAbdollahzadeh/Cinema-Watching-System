package ir.maktabsharif.cinemawatchingsystem.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public final class  EntityManagerProvider {
    public static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("cinemaWatchingSystem");
    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}
