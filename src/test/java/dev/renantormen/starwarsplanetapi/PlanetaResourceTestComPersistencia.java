package dev.renantormen.starwarsplanetapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.Bean;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.jboss.weld.junit.MockBean;
import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldJunit5Extension;
import org.jboss.weld.junit5.WeldSetup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import dev.renantormen.starwarsplanetapi.DAO.PlanetaDAO;
import dev.renantormen.starwarsplanetapi.client.StarWarsAPIClient;
import dev.renantormen.starwarsplanetapi.dto.PlanetaDTO;
import dev.renantormen.starwarsplanetapi.mocks.PlanetaDTOMock;
import dev.renantormen.starwarsplanetapi.resources.PlanetResource;
import dev.renantormen.starwarsplanetapi.services.PlanetaService;
import dev.renantormen.starwarsplanetapi.services.SwAPIService;
import dev.renantormen.starwarsplanetapi.utils.EntityManagerFactoryParaTestes;

@ExtendWith(WeldJunit5Extension.class)
public class PlanetaResourceTestComPersistencia {
    @Inject
    private EntityManagerFactoryParaTestes emf;

    @WeldSetup
    private WeldInitiator weld = WeldInitiator
            .from(EntityManagerFactoryParaTestes.class, PlanetResource.class, PlanetaService.class, PlanetaDAO.class, SwAPIService.class)
            .addBeans(mockSwAPI()).setPersistenceContextFactory(injection -> emf.getEntityManager())
            .activate(ApplicationScoped.class).build();

    @Test
    public void responseDeveSerOKAoSalvarSemErros() {
        emf.getEntityManager().getTransaction().begin();
        Response response = getResource().salvarPlaneta(PlanetaDTOMock.getMock());
        assertEquals(Response.ok().build().getStatusInfo(), response.getStatusInfo());
    }

    @Test
    public void responseDeveSerServerErrorAoSalvarComErros() {
        Response response = getResource().salvarPlaneta(null);
        assertEquals(Response.serverError().build().getStatusInfo(), response.getStatusInfo());
    }

    @Test
    public void responseDeveSerNoContentQuandoNaoEncontrarPorId() {
        Response response = getResource().buscarPorId(Long.valueOf(0));
        assertEquals(Response.noContent().build().getStatusInfo(), response.getStatusInfo());
    }

    @Test
    public void responseDeveSerOKQuandoEncontrarPorId() {
        PlanetaDTO planetaSalvo = salvarPlanetaPeloResource();
        Response response = getResource().buscarPorId(planetaSalvo.getId());
        PlanetaDTO planetaResponse = (PlanetaDTO) response.getEntity();
        assertEquals(planetaSalvo.getId(), planetaResponse.getId());
        assertEquals(Response.ok().build().getStatusInfo(), response.getStatusInfo());
    }


    @Test
    public void responseDeveSerNoContentQuandoNaoEncontrarPorNome() {
        salvarPlanetaPeloResource();
        Response response = getResource().buscarPorNome("Não vai dar não");
        assertEquals(Response.noContent().build().getStatusInfo(), response.getStatusInfo());
    }

    @Test
    public void responseDeveSerOKQuandoEncontrarPorNome() {
        salvarPlanetaPeloResource();
        Response response = getResource().buscarPorNome("Planeta Teste");
        List<PlanetaDTO> planetas = (List<PlanetaDTO>) response.getEntity();
        assertFalse(planetas.isEmpty());
        assertEquals(Response.ok().build().getStatusInfo(), response.getStatusInfo());
    }

    @Test
    public void responseDeveSerOKQuandoAtualizarValido() {
        PlanetaDTO planetaSalvo = salvarPlanetaPeloResource();
        PlanetaDTO planetaMock = PlanetaDTOMock.getMock();
        planetaMock.setId(planetaSalvo.getId());
        planetaMock.setNome("Planeta Atualizado");
        Response response = getResource().atualizarPlaneta(planetaMock);
        PlanetaDTO planetaDoResponse = (PlanetaDTO) response.getEntity();
        assertEquals(planetaSalvo.getId(), planetaDoResponse.getId());
        assertNotEquals(planetaDoResponse.getNome(), planetaSalvo.getNome());
        assertEquals(Response.ok().build().getStatusInfo(), response.getStatusInfo());
    }

    @Test
    public void responseDeveSerServerErrorQuandoAtualizarInvalido() {
        Response response = getResource().atualizarPlaneta(null);
        assertEquals(Response.serverError().build().getStatusInfo(), response.getStatusInfo());
    }

    @Test
    public void responseDeveSerOkQuandoListarTodos() {
        salvarPlanetaPeloResource();
        Response response = getResource().listarTodos();
        List<PlanetaDTO> planetas = (List<PlanetaDTO>) response.getEntity();
        assertFalse(planetas.isEmpty());
        assertEquals(Response.ok().build().getStatusInfo(), response.getStatusInfo());
    }

    static Bean<?> mockSwAPI() {
        return MockBean.builder().types(StarWarsAPIClient.class).scope(ApplicationScoped.class)
                .creating(Mockito.when(Mockito.mock(StarWarsAPIClient.class).contarOcorrenciasDeFilmeDoPlanetaPorNome("Planeta Teste")).thenReturn(1).getMock())
                .build();
    }

    private PlanetResource getResource() {
        return weld.select(PlanetResource.class).get();
    }

    private PlanetaDTO salvarPlanetaPeloResource() {
        emf.getEntityManager().getTransaction().begin();
        return (PlanetaDTO) getResource().salvarPlaneta(PlanetaDTOMock.getMock()).getEntity();
    }
}
