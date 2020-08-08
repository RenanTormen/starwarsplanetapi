package dev.renantormen.starwarsplanetapi.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import dev.renantormen.starwarsplanetapi.DAO.PlanetaDAO;
import dev.renantormen.starwarsplanetapi.dto.PlanetaDTO;
import dev.renantormen.starwarsplanetapi.entities.Planeta;
import dev.renantormen.starwarsplanetapi.utils.DTOConverter;

@ApplicationScoped
@Transactional
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class PlanetaService {

    @Inject
    private PlanetaDAO planetaDAO;

    public PlanetaDTO inserirPlaneta(PlanetaDTO planetaDTO) {
        Planeta planeta = planetaDAO.insert(DTOConverter.toEntity(Planeta.class, planetaDTO));
        return DTOConverter.toDTO(PlanetaDTO.class, planeta);
    }

    public PlanetaDTO atualizarPlaneta(PlanetaDTO planetaDTO) {
        Planeta planeta = planetaDAO.update(DTOConverter.toEntity(Planeta.class, planetaDTO));
        return DTOConverter.toDTO(PlanetaDTO.class, planeta);
    }

    public void deletarPlaneta(Long id) {
        planetaDAO.delete(id);
    }

    public PlanetaDTO retornarPlanetaPorId(Long id) {
        Planeta planeta = planetaDAO.localizarPorId(id);
        return DTOConverter.toDTO(PlanetaDTO.class, planeta);
    }

    public List<PlanetaDTO> retornarPlanetaPorNome(String nome) {
        List<PlanetaDTO> resultados = new ArrayList<>();
        planetaDAO.localizarPorNome(nome).forEach(planeta -> resultados.add(DTOConverter.toDTO(PlanetaDTO.class, planeta)));
        return resultados;
    }

    public List<PlanetaDTO> listarPlanetas() {
        List<PlanetaDTO> listaPlanetaDTO = new ArrayList<>();
        planetaDAO.listarTodos().forEach(planeta -> listaPlanetaDTO.add(DTOConverter.toDTO(PlanetaDTO.class, planeta)));
        return listaPlanetaDTO;
    }

}