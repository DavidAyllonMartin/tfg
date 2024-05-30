package org.ielena.pokedex.services;

import org.ielena.pokedex.models.PokemonModel;
import org.ielena.pokedex.models.TypeModel;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PokemonService {

    List<PokemonModel> findAll();

    Optional<PokemonModel> findById(Long id);

    List<PokemonModel> findByType(TypeModel type);

    List<PokemonModel> findByName(String name);

    void delete(Long id);

    PokemonModel save(PokemonModel pokemon);

    void saveAll(Collection<PokemonModel> pokemons);

}
