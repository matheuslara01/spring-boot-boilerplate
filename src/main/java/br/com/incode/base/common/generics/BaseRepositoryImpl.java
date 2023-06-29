package br.com.incode.base.common.generics;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import br.com.incode.base.common.exceptions.EntityDeleteException;
import br.com.incode.base.common.exceptions.EntityPersistenceException;
import br.com.incode.base.models.dto.Response;

public abstract class BaseRepositoryImpl<T, ID extends Serializable> implements BaseService<T, ID> {

    @Autowired
    private BaseRepository<T, ID> baseRepository;

    @Autowired
    private Logger logger;

    @Override
    public boolean save(T entity) {
        try {
            baseRepository.saveAndFlush(entity);
            return true;
        } catch (Exception ex) {
            logger.error("BaseRepository.save: " + entity.getClass() + " Erro: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public void save(T entity, String errorMessage) {
        try {
            baseRepository.saveAndFlush(entity);
        } catch (Exception ex) {
            logger.error("BaseRepository.save: " + entity.getClass() + " Erro: " + ex.getMessage());
            throw new EntityPersistenceException(errorMessage);
        }
    }

    @Override
    public boolean saveAllAndFlush(List<T> listEntity) {
        try {
            baseRepository.saveAllAndFlush(listEntity);
            return true;
        } catch (Exception ex) {
            logger.error("BaseRepository.saveAllAndFlush: " + listEntity.getClass() + " Erro: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public void saveAllAndFlush(List<T> listEntity, String errorMessage) {
        try {
            baseRepository.saveAllAndFlush(listEntity);
        } catch (Exception ex) {
            logger.error("BaseRepository.saveAllAndFlush: " + listEntity.getClass() + " Erro: " + ex.getMessage());
            throw new EntityPersistenceException(errorMessage);
        }
    }

    @Override
    public List<T> saveAllAndFlushReturnList(List<T> listEntity) {
        return baseRepository.saveAllAndFlush(listEntity);
    }

    @Override
    public List<T> saveAllAndFlushReturnList(List<T> listEntity, String errorMessage) {
        try {
            return baseRepository.saveAllAndFlush(listEntity);
        } catch (Exception ex) {
            logger.error(
                    "BaseRepository.saveAllAndFlushReturnList: " + listEntity.getClass() + " Erro: " + ex.getMessage());
            throw new EntityPersistenceException(errorMessage);
        }

    }

    @Override
    public T saveReturnEntity(T entity) {
        return baseRepository.saveAndFlush(entity);
    }

    @Override
    public T saveReturnEntity(T entity, String errorMessage) {
        try {
            return baseRepository.saveAndFlush(entity);
        } catch (Exception ex) {
            logger.error("BaseRepository.saveReturnEntity: " + entity.getClass() + " Erro: " + ex.getMessage());
            throw new EntityPersistenceException(errorMessage);
        }
    }

    @Override
    public List<T> findAll() {
        return baseRepository.findAll();
    }

    @Override
    public List<T> findAll(Specification<T> spec) {
        return baseRepository.findAll(spec);
    };

    @Override
    public Page<T> findAll(Specification<T> spec, Integer page) {
        return baseRepository.findAll(spec, PageRequest.of(page, 4));
    };

    @Override
    public Page<T> findAll(Integer page) {
        return baseRepository.findAll(PageRequest.of(page, 4));
    };

    @Override
    public Optional<T> findByIdOptional(ID entityId) {
        return baseRepository.findById(entityId);
    }

    @Override
    public T findById(ID entityId) {
        return baseRepository.findById(entityId).orElseThrow(() -> new NoResultException("Recurso não encontrado!"));
    }

    @Override
    public T update(T entity) {
        return (T) baseRepository.saveAndFlush(entity);
    }

    @Override
    public T updateById(T entity, ID entityId) {
        Optional<T> optional = baseRepository.findById(entityId);
        if (optional.isPresent()) {
            return (T) baseRepository.saveAndFlush(entity);
        } else {
            return null;
        }
    }

    @Override
    public boolean delete(T entity) {
        try {
            baseRepository.delete(entity);
            return true;
        } catch (Exception e) {
            logger.error("BaseRepository.delete: " + entity.getClass() + " Erro: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void delete(T entity, String errorMessage) {
        try {
            baseRepository.delete(entity);
        } catch (Exception ex) {
            logger.error("BaseRepository.delete: " + entity.getClass() + " Erro: " + ex.getMessage());
            throw new EntityDeleteException(errorMessage);
        }
    }

    @Override
    public Response deleteById(ID entityId, String errorMessage) {
        try {
            baseRepository.deleteById(entityId);
            return new Response(true, "Excluido com sucesso!");
        } catch (Exception e) {
            logger.error("BaseRepository.deleteById: " + entityId.getClass() + " Erro: " + e.getMessage());
            throw new EntityDeleteException(errorMessage);
        }
    }

    @Override
    public Response deleteAll(List<T> listEntit, String errorMessage) {
        try {
            baseRepository.deleteAll(listEntit);
            return new Response(true, "Excluido com sucesso!");
        } catch (Exception e) {
            logger.error("BaseRepository.deleteAll: " + listEntit.getClass() + " Erro: " + e.getMessage());
            throw new EntityDeleteException(errorMessage);
        }
    }

}
