package dev.renantormen.starwarsplanetapi.utils;

import java.util.Objects;

import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactoryParaTestes {

    private EntityManagerFactory createEntityManagerFactory = Persistence.createEntityManagerFactory("TestesPU");
    private EntityManager em = null;
    
    @Produces
    public EntityManager getEntityManager(){
        if(Objects.isNull(em)){
            em = createEntityManagerFactory.createEntityManager();
        }
        return em;
    }
 
    public void fecharEntityManager(@Disposes EntityManager em){
        em.close();
    }

    
}