package dev.renantormen.starwarsplanetapi.resources;

import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dev.renantormen.starwarsplanetapi.dto.PlanetSwapiDTO;
import dev.renantormen.starwarsplanetapi.dto.PlanetaDTO;
import dev.renantormen.starwarsplanetapi.enums.EnumPathResources;
import dev.renantormen.starwarsplanetapi.services.PlanetaService;
import dev.renantormen.starwarsplanetapi.services.SwAPIService;

@Path(EnumPathResources.PLANETAS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PlanetResource {

    private static final Logger LOGGER = Logger.getLogger("PlanetResource");

    @Inject
    private PlanetaService planetaService;

    @Inject
    private SwAPIService swApiService;

    @GET
    public Response listarTodos() {
        try {
            List<PlanetaDTO> listPlanetas = planetaService.listarPlanetas();
            if (listPlanetas.isEmpty()) {
                return Response.noContent().build();
            }
            return Response.ok(listPlanetas).build();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Houve um erro na busca por ID ", e);
            return Response.serverError().build();
        }
    }

    @GET
    @Path("{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        try {
            PlanetaDTO planeta = planetaService.retornarPlanetaPorId(id);
            if (Objects.isNull(planeta)) {
                return Response.noContent().build();
            }
            return Response.ok(planeta).build();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Houve um erro na busca por ID ", e);
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/nome/{nome}")
    public Response buscarPorNome(@PathParam("nome") String nome) {
        try {
            List<PlanetaDTO> planetas = planetaService.retornarPlanetaPorNome(nome);
            if (planetas.isEmpty()) {
                return Response.noContent().build();
            }
            return Response.ok(planetas).build();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Houve um erro na busca por nome ", e);
            return Response.serverError().build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        try {
            planetaService.deletarPlaneta(id);
            return Response.ok("Planeta deletado com sucesso").build();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Houve um erro ao deletar  ", e);
            return Response.serverError().build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response atualizarPlaneta(PlanetaDTO planetaDTO) {
        try {
            PlanetaDTO planeta = planetaService.atualizarPlaneta(planetaDTO);
            return Response.ok(planeta).build();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Houve um erro ao atualizar ", e);
            return Response.serverError().build();
        }
    }

    @POST
    public Response salvarPlaneta(PlanetaDTO planetaDTO) {
        try {
            planetaDTO.setAparicoesEmFilmes(swApiService.contarFilmesDoPlanetaPorNome(planetaDTO.getNome()));
            PlanetaDTO planeta = planetaService.inserirPlaneta(planetaDTO);
            return Response.ok(planeta).build();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Houve um erro ao salvar ", e);
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/swapi/")
    public Response recuperarTodosOsPlanetasDaSwAPI(){
        List<PlanetSwapiDTO> planetas = swApiService.recuperarTodosOsPlanetasDaSwApi();
        return Response.ok(planetas).build();
    }
}