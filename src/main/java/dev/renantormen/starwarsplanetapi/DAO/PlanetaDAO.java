package dev.renantormen.starwarsplanetapi.DAO;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import dev.renantormen.starwarsplanetapi.entities.Planeta;

public class PlanetaDAO extends GenericDAO<Planeta, Long> {

    private static final Logger LOGGER = Logger.getLogger("PlanetaDAO");

    public List<Planeta> listarTodos() {
        TypedQuery<Planeta> query = getEntityManager().createQuery("SELECT p from Planeta p", Planeta.class);
        return query.getResultList();
    }

    public Planeta localizarPorNome(String nome) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT p FROM Planeta p ");
            sb.append("WHERE p.nome LIKE :nome ");
            TypedQuery<Planeta> typedQuery = getEntityManager().createQuery(sb.toString(), Planeta.class);
            typedQuery.setParameter("nome", nome);
            return typedQuery.getSingleResult();    
        } catch (NoResultException e) {
            return null;
        } catch (Exception e){
            LOGGER.log(Level.SEVERE, "Houve um erro ao executar a busca", e);
        }
        return null;
    } 

}