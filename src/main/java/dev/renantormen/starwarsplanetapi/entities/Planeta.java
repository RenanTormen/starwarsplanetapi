package dev.renantormen.starwarsplanetapi.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Planeta {

    @Id
    @SequenceGenerator(name = "IDPLANETA", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "IDPLANETA", strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String clima;

    private String terreno;

    private int aparicoesEmFilmes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getAparicoesEmFilmes() {
        return aparicoesEmFilmes;
    }

    public void setAparicoesEmFilmes(int aparicoesEmFilmes) {
        this.aparicoesEmFilmes = aparicoesEmFilmes;
    }

    public String getTerreno() {
        return terreno;
    }

    public void setTerreno(String terreno) {
        this.terreno = terreno;
    }

}
