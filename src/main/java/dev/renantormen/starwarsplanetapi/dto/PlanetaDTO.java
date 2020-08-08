package dev.renantormen.starwarsplanetapi.dto;

public class PlanetaDTO extends DTOGenerico {

    private String nome;
    private String clima;
    private String terreno;
    private int aparicoesEmFilmes;

    public PlanetaDTO() {

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getClima() {
        return clima;
    }

    public void setClima(String clima) {
        this.clima = clima;
    }

    public String getTerreno() {
        return terreno;
    }

    public void setTerreno(String terreno) {
        this.terreno = terreno;
    }


    
    public int getAparicoesEmFilmes() {
        return aparicoesEmFilmes;
    }

    public void setAparicoesEmFilmes(int aparicoesEmFilmes) {
        this.aparicoesEmFilmes = aparicoesEmFilmes;
    }

}