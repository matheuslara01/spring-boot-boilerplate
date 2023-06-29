package br.com.incode.base.common.generics;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import br.com.incode.base.models.Response;

public interface BaseService<T, ID extends Serializable> {

    public abstract boolean save(T entity);

    public abstract void save(T entity, String errorMessage);

    public abstract boolean saveAllAndFlush(List<T> listEntity);

    public abstract void saveAllAndFlush(List<T> listEntity, String errorMessage);

    public abstract List<T> saveAllAndFlushReturnList(List<T> listEntity);

    public abstract List<T> saveAllAndFlushReturnList(List<T> listEntity, String errorMessage);

    public abstract T saveReturnEntity(T entity);

    public abstract T saveReturnEntity(T entity, String errorMessage);

    public abstract List<T> findAll();

    public abstract List<T> findAll(Specification<T> spec);

    public abstract Page<T> findAll(Specification<T> spec, Integer page);

    public abstract Page<T> findAll(Integer page);

    public abstract Optional<T> findByIdOptional(ID entityId);

    // public abstract D findByIdTeste(ID entityId, Class<D> dtoClass);

    public abstract T findById(ID entityId);

    public abstract T update(T entity);

    public abstract T updateById(T entity, ID entityId);

    public abstract boolean delete(T entity);

    public abstract void delete(T entity, String errorMessage);

    public abstract Response deleteById(ID entityId, String errorMessage);

    public abstract Response deleteAll(List<T> listEntity, String errorMessage);

}
