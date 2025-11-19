package ir.maktabsharif.cinemawatchingsystem.service.impl;

import ir.maktabsharif.cinemawatchingsystem.model.base.BaseModel;
import ir.maktabsharif.cinemawatchingsystem.service.BaseService;
import ir.maktabsharif.cinemawatchingsystem.util.EntityManagerProvider;
import ir.maktabsharif.cinemawatchingsystem.repository.BaseRepository;
import jakarta.persistence.EntityManager;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class BaseServiceImpl<T extends BaseModel<ID>, ID extends Serializable> implements BaseService<T, ID> {
    private BaseRepository<T, ID> baseRepository;
    protected EntityManager em;

    public BaseServiceImpl(BaseRepository<T, ID> baseRepository) {
        this.baseRepository = baseRepository;
    }

    @Override
    public T save(T t) {
        em = EntityManagerProvider.getEntityManager();
        baseRepository.setRepositoryEntityManager(em);
        try {
            em.getTransaction().begin();
            t = baseRepository.save(t);
            em.getTransaction().commit();
            return t;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            if (em.isOpen())
                em.close();
        }
    }

    @Override
    public Optional<T> findById(ID id) {
        em = EntityManagerProvider.getEntityManager();
        baseRepository.setRepositoryEntityManager(em);
        try {
            return baseRepository.findById(id);
        } finally {
            if (em.isOpen())
                em.close();
        }
    }

    @Override
    public List<T> findAll() {
        em = EntityManagerProvider.getEntityManager();
        baseRepository.setRepositoryEntityManager(em);
        try {
            return baseRepository.findAll();
        } finally {
            if (em.isOpen())
                em.close();
        }
    }

    @Override
    public T update(T t) {
        em = EntityManagerProvider.getEntityManager();
        baseRepository.setRepositoryEntityManager(em);
        try {
            em.getTransaction().begin();
            t = baseRepository.update(t);
            em.getTransaction().commit();
            return t;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            if (em.isOpen())
                em.close();
        }
    }

    @Override
    public T delete(T t) {
        em = EntityManagerProvider.getEntityManager();
        baseRepository.setRepositoryEntityManager(em);
        try {
            em.getTransaction().begin();
            t = baseRepository.delete(t);
            em.getTransaction().commit();
            return t;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            if (em.isOpen())
                em.close();
        }
    }
}
