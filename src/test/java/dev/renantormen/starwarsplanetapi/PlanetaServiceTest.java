package dev.renantormen.starwarsplanetapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldJunit5Extension;
import org.jboss.weld.junit5.WeldSetup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import dev.renantormen.starwarsplanetapi.DAO.PlanetaDAO;
import dev.renantormen.starwarsplanetapi.dto.PlanetaDTO;
import dev.renantormen.starwarsplanetapi.mocks.PlanetaDTOMock;
import dev.renantormen.starwarsplanetapi.services.PlanetaService;
import dev.renantormen.starwarsplanetapi.utils.EntityManagerFactoryParaTestes;

@ExtendWith(WeldJunit5Extension.class)
public class PlanetaServiceTest {
   
    @Inject
    private EntityManagerFactoryParaTestes emf;

    @WeldSetup
    private WeldInitiator weld = WeldInitiator.
    from(EntityManagerFactoryParaTestes.class, PlanetaService.class, PlanetaDAO.class)
    .setPersistenceContextFactory(injection -> emf.getEntityManager())
    .activate(ApplicationScoped.class).build();

    @Inject
    private PlanetaService planetaService;

    @Test
    public void deveInserirPlaneta(){
        PlanetaDTO planeta = inserirPlanetaPeloService();
        assertNotNull(planeta);
        assertNotNull(planeta.getId());
    }

    @Test
    public void deveListarTodosOsInseridos(){
        inserirPlanetaPeloService();
        List<PlanetaDTO> planetas = planetaService.listarPlanetas();
        assertFalse(planetas.isEmpty());
    }

    @Test
    public void deveListarPlanetaPorId(){
        PlanetaDTO planetaInserido = inserirPlanetaPeloService();
        PlanetaDTO planeta = planetaService.retornarPlanetaPorId(planetaInserido.getId());
        assertNotNull(planeta);
        assertEquals(planeta.getId(),planetaInserido.getId());
    }
    @Test
    public void deveListarPlanetasPorNome(){
        inserirPlanetaPeloService();
        String nome = "Planeta Teste";
        List<PlanetaDTO> planeta = planetaService.retornarPlanetaPorNome(nome);
        assertNotNull(planeta);
        assertFalse(planeta.isEmpty());
    }

    @Test
    public void deveDeletarPlanetaInserido(){   
        PlanetaDTO planetaInserido = inserirPlanetaPeloService();
        planetaService.deletarPlaneta(planetaInserido.getId());
        PlanetaDTO planetaRetornado = planetaService.retornarPlanetaPorId(planetaInserido.getId());
        assertNull(planetaRetornado);
    }

    @Test
    public void deveAtualizarPlaneta(){
        PlanetaDTO planetaInserido = inserirPlanetaPeloService();
        assertNotNull(planetaInserido);
        String nomeAnterior = planetaInserido.getNome();
        planetaInserido.setNome("NomeAlterado");
        planetaService.atualizarPlaneta(planetaInserido);
        PlanetaDTO planetaRetornado = planetaService.retornarPlanetaPorId(planetaInserido.getId());
        assertEquals(planetaRetornado.getId(),planetaInserido.getId());
        assertNotEquals(planetaRetornado.getNome(), nomeAnterior);
    }

    private PlanetaDTO inserirPlanetaPeloService() {
        emf.getEntityManager().getTransaction().begin();
        return planetaService.inserirPlaneta(PlanetaDTOMock.getMock());
    }
    
}