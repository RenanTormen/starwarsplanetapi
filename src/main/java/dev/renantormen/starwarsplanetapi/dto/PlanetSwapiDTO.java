package dev.renantormen.starwarsplanetapi.dto;

public class PlanetSwapiDTO {

    private String name;

    private String terrain;

    private String climate;

    private int filmOcurrences;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClimate() {
        return climate;
    }

    public void setClimate(String climate) {
        this.climate = climate;
    }

    public int getFilmOcurrences() {
        return filmOcurrences;
    }

    public void setFilmOcurrences(int filmOcurrences) {
        this.filmOcurrences = filmOcurrences;
    }

    public String getTerrain() {
        return terrain;
    }

    public void setTerrain(String terrain) {
        this.terrain = terrain;
    }

}