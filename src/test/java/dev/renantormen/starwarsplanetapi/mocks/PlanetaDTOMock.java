package dev.renantormen.starwarsplanetapi.mocks;

import dev.renantormen.starwarsplanetapi.dto.PlanetaDTO;

public class PlanetaDTOMock {

    public static PlanetaDTO getMock(){
       PlanetaDTO planeta = new PlanetaDTO();
       planeta.setNome("Planeta Teste"); 
       planeta.setClima("Clima Teste");
       planeta.setTerreno("Terreno Teste");
       return planeta;
    }
    
}