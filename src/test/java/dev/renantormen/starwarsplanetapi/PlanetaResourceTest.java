package dev.renantormen.starwarsplanetapi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.Bean;
import javax.ws.rs.core.Response;

import org.jboss.weld.junit.MockBean;
import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldJunit5Extension;
import org.jboss.weld.junit5.WeldSetup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import dev.renantormen.starwarsplanetapi.DAO.GenericDAO;
import dev.renantormen.starwarsplanetapi.DAO.PlanetaDAO;
import dev.renantormen.starwarsplanetapi.client.StarWarsAPIClient;
import dev.renantormen.starwarsplanetapi.dto.PlanetaDTO;
import dev.renantormen.starwarsplanetapi.resources.PlanetResource;
import dev.renantormen.starwarsplanetapi.services.PlanetaService;
import dev.renantormen.starwarsplanetapi.services.SwAPIService;

@ExtendWith(WeldJunit5Extension.class)
public class PlanetaResourceTest {

    @WeldSetup
    private WeldInitiator weld = WeldInitiator
            .from(PlanetResource.class, PlanetaDAO.class, SwAPIService.class, GenericDAO.class)
            .addBeans(mockSwAPI(), mockService()).activate(ApplicationScoped.class).build();

    @Test
    public void responseDeveSerOKAoSalvarSemErros() {
        PlanetaDTO planetaDTO = getPlanetaDTO();
        Response response = getResource().salvarPlaneta(planetaDTO);
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
        Response response = getResource().buscarPorId(Long.valueOf(1));
        assertEquals(Response.ok().build().getStatusInfo(), response.getStatusInfo());
    }
    
    @Test
    public void responseDeveSerNoContentQuandoNaoEncontrarPorNome() {
        Response response = getResource().buscarPorNome("Não vai dar não");
        assertEquals(Response.noContent().build().getStatusInfo(), response.getStatusInfo());
    }

    @Test
    public void responseDeveSerOKQuandoEncontrarPorNome() {
        Response response = getResource().buscarPorNome("Teste");
        assertEquals(Response.ok().build().getStatusInfo(), response.getStatusInfo());
    }
    
    @Test
    public void responseDeveSerOKQuandoAtualizarValido() {
        Response response = getResource().atualizarPlaneta(getPlanetaDTO());
        assertEquals(Response.ok().build().getStatusInfo(), response.getStatusInfo());
    }
    
    @Test
    public void responseDeveSerServerErrorQuandoAtualizarInvalido() {
        Response response = getResource().atualizarPlaneta(null);
        assertEquals(Response.serverError().build().getStatusInfo(), response.getStatusInfo());
    }

    @Test
    public void responseDeveSerOkQuandoListarTodos(){
        Response response = getResource().listarTodos();
        assertEquals(Response.ok().build().getStatusInfo(), response.getStatusInfo());
    }
    
    @Test


    static Bean<?> mockService() {
        PlanetaService mock = Mockito.mock(PlanetaService.class);
        Object metodoInsercao = Mockito.when(mock.inserirPlaneta(getPlanetaDTO())).thenReturn(new PlanetaDTO()).getMock();
        Object findIdInvalido = Mockito.when(mock.retornarPlanetaPorId(Long.valueOf(0))).thenReturn(null).getMock();
        Object findIdValido = Mockito.when(mock.retornarPlanetaPorId(Long.valueOf(1))).thenReturn(getPlanetaDTO()).getMock();
        Object atualizar = Mockito.when(mock.atualizarPlaneta(getPlanetaDTO())).thenReturn(new PlanetaDTO()).getMock();
        Object atualizarInvalido = Mockito.when(mock.atualizarPlaneta(null)).thenThrow(new RuntimeException()).getMock();
        Object findPorNome = Mockito.when(mock.retornarPlanetaPorNome("Teste")).thenReturn(getPlanetaDTO()).getMock();
        Object listarTodos = Mockito.when(mock.listarPlanetas()).thenReturn(Arrays.asList(getPlanetaDTO())).getMock();

        return MockBean.builder().types(PlanetaService.class).scope(ApplicationScoped.class)
        .creating(metodoInsercao)
        .creating(findIdInvalido)
        .creating(findIdValido)
        .creating(atualizar)
        .creating(atualizarInvalido)
        .creating(findPorNome)
        .creating(listarTodos)
        .build();
    }

    private static PlanetaDTO getPlanetaDTO() {
        PlanetaDTO planetaDTO = new PlanetaDTO();
        planetaDTO.setNome("Teste");
        planetaDTO.setClima("Clima teste");
        planetaDTO.setTerreno("Terreno Teste");
        planetaDTO.setAparicoesEmFilmes(1);
        return planetaDTO;
    }

    static Bean<?> mockSwAPI() {
        return MockBean.builder().types(StarWarsAPIClient.class).scope(ApplicationScoped.class)
                .creating(Mockito
                        .when(Mockito.mock(StarWarsAPIClient.class).contarOcorrenciasDeFilmeDoPlanetaPorNome("Teste"))
                        .thenReturn(1).getMock())
                .build();
    }


    private PlanetResource getResource() {
        return weld.select(PlanetResource.class).get();
    }

}