package ir.maktabsharif.cinemawatchingsystem.repository;

import ir.maktabsharif.cinemawatchingsystem.model.base.BaseModel;
import jakarta.persistence.EntityManager;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface BaseRepository<T extends BaseModel<ID>, ID extends Serializable> {

    T save(T t);

    Optional<T> findById(ID id);

    List<T> findAll();

    T update(T t);

    T delete(T t);

    void setRepositoryEntityManager(EntityManager em);

    Class<T> getModelClass();
}
