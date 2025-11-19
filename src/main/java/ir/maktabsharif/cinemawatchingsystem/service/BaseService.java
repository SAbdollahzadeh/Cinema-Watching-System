package ir.maktabsharif.cinemawatchingsystem.service;

import ir.maktabsharif.cinemawatchingsystem.model.base.BaseModel;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface BaseService<T extends BaseModel<ID>, ID extends Serializable> {
    T save(T t);

    Optional<T> findById(ID id);
    List<T> findAll();

    T update(T t);

    T delete(T t);
}
