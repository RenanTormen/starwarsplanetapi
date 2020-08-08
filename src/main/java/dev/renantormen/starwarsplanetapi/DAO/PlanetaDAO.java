package dev.renantormen.starwarsplanetapi.DAO;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.TypedQuery;

import dev.renantormen.starwarsplanetapi.entities.Planeta;

public class PlanetaDAO extends GenericDAO<Planeta, Long> {

    private static final Logger LOGGER = Logger.getLogger("PlanetaDAO");

    public List<Planeta> listarTodos() {
        TypedQuery<Planeta> query = getEntityManager().createQuery("SELECT p from Planeta p", Planeta.class);
        return query.getResultList();
    }

    public List<Planeta> localizarPorNome(String nome) {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT p FROM Planeta p ");
            sb.append("WHERE p.nome = :nome ");
            TypedQuery<Planeta> typedQuery = getEntityManager().createQuery(sb.toString(), Planeta.class);
            typedQuery.setParameter("nome", nome);
            return typedQuery.getResultList();    
    } 

}