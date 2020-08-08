package dev.renantormen.starwarsplanetapi.services;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import dev.renantormen.starwarsplanetapi.client.StarWarsAPIClient;
import dev.renantormen.starwarsplanetapi.dto.PlanetSwapiDTO;

@ApplicationScoped
public class SwAPIService {

    @Inject
    StarWarsAPIClient swAPI;

    public SwAPIService(){
        //Default
    }

    public int contarFilmesDoPlanetaPorNome(String nome) {
        return swAPI.contarOcorrenciasDeFilmeDoPlanetaPorNome(nome);
    }

    public List<PlanetSwapiDTO> recuperarTodosOsPlanetasDaSwApi() {
        return swAPI.retornarTodosOsPlanetas();
    }

}