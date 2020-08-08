package dev.renantormen.starwarsplanetapi.client;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dev.renantormen.starwarsplanetapi.dto.PlanetSwapiDTO;
import dev.renantormen.starwarsplanetapi.enums.EnumPathResources;
import dev.renantormen.starwarsplanetapi.utils.StringUtil;

@ApplicationScoped
public class StarWarsAPIClient {

    private Client client;
    private WebTarget apiTarget;
    private WebTarget planetsURL;

    public StarWarsAPIClient() {
        client = ClientBuilder.newClient();
        apiTarget = client.target(EnumPathResources.SWAPI);
        planetsURL = apiTarget.path("/planets/");
    }

    public List<PlanetSwapiDTO> retornarTodosOsPlanetas() {
        Response response = planetsURL.request(MediaType.APPLICATION_JSON).get();
        List<PlanetSwapiDTO> planetas = new ArrayList<>();
        JsonObject readEntity = response.readEntity(JsonObject.class);
        adicionarPlaneta(readEntity, planetas);
        preencherListaEnquantoHouverProximo(planetsURL, planetas, readEntity);
        return planetas;
    }

    private void preencherListaEnquantoHouverProximo(WebTarget planets, List<PlanetSwapiDTO> planetas,
            JsonObject readEntity) {
        boolean hasNext;
        do {
            String nextUrl = readEntity.getString("next");
            WebTarget next = planets.queryParam("page", nextUrl.split("page=")[1]);
            Response responseNext = next.request(MediaType.APPLICATION_JSON).get();
            readEntity = responseNext.readEntity(JsonObject.class);
            adicionarPlaneta(readEntity, planetas);
            hasNext = !readEntity.get("next").equals(JsonValue.NULL);
        } while (hasNext);
    }

    private void adicionarPlaneta(JsonObject readEntity, List<PlanetSwapiDTO> planetas) {
        readEntity.getJsonArray("results").forEach(object -> {
            PlanetSwapiDTO planeta = new PlanetSwapiDTO();
            planeta.setName(object.asJsonObject().getString("name"));
            planeta.setTerrain(object.asJsonObject().getString("terrain"));
            planeta.setFilmOcurrences(object.asJsonObject().getJsonArray("films").size());
            planeta.setClimate(object.asJsonObject().getString("climate"));
            planetas.add(planeta);
        });
    }

    public int contarOcorrenciasDeFilmeDoPlanetaPorNome(String nome) {
        WebTarget planets = planetsURL.queryParam("search", StringUtil.formatarPrimeiraLetraUppercase(nome));
        Response response = planets.request(MediaType.APPLICATION_JSON).get();
        JsonObject readEntity = response.readEntity(JsonObject.class);
        return readEntity.getJsonArray("results").get(0).asJsonObject().getJsonArray("films").size();
    }

}