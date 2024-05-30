package org.ielena.pokedex.facades;

import org.ielena.pokedex.dtos.PokemonDto;
import org.ielena.pokedex.models.TypeModel;

import java.util.List;
import java.util.Optional;

public interface PokemonFacade {

    List<PokemonDto> findAll();

    Optional<PokemonDto> findById(Long id);

    List<PokemonDto> findByType(TypeModel type);

    List<PokemonDto> findByName(String name);
}
