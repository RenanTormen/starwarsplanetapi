package dev.renantormen.starwarsplanetapi.DAO;

import java.lang.reflect.ParameterizedType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class GenericDAO<E, Id> {

    @PersistenceContext(name = "ApiPU")
    private EntityManager entityManager;

    public E localizarPorId(Id id) {
        return getEntityManager().find(getClasseDaEntidade(), id);
    }

    public E insert(E entity) {
        getEntityManager().persist(entity);
        getEntityManager().flush();
        return entity;
    }

    public E update(E entity) {
        return getEntityManager().merge(entity);
    }

    public void delete(Id id) {
        getEntityManager().remove(localizarPorId(id));
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    private Class<E> getClasseDaEntidade() {
        return (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

}