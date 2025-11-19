package ir.maktabsharif.cinemawatchingsystem.repository.impl;

import ir.maktabsharif.cinemawatchingsystem.model.base.BaseModel;
import ir.maktabsharif.cinemawatchingsystem.repository.BaseRepository;
import jakarta.persistence.EntityManager;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class BaseRepositoryImpl<T extends BaseModel<ID>, ID extends Serializable> implements BaseRepository<T, ID> {

    protected EntityManager em;
    protected Class<T> entityClass;

    public BaseRepositoryImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public void setRepositoryEntityManager(EntityManager em) {
        this.em = em;
    }

    @Override
    public T save(T t) {
        if (t.getId() == null) {
            em.persist(t);
        } else {
            t = em.merge(t);
        }
        return t;
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(em.find(entityClass, id));
    }

    @Override
    public List<T> findAll() {
        return em.createQuery("from " + entityClass.getSimpleName(), entityClass).getResultList();
    }

    @Override
    public T update(T t) {
        return em.merge(t);
    }

    @Override
    public T delete(T t) {
        if (em.contains(t)) {
            em.remove(t);
        } else {
            t = em.merge(t);
            em.remove(t);
        }
        return t;
    }
}
