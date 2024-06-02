package org.ielena.pokedex.services;

import org.ielena.pokedex.dtos.PokemonDto;
import org.ielena.pokedex.models.PokemonModel;
import org.ielena.pokedex.models.TypeModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PokemonService {

    List<PokemonModel> findAll();

    Page<PokemonModel> findAll(Pageable pageable);

    Optional<PokemonModel> findById(Long id);

    List<PokemonModel> findByType(TypeModel type);

    Page<PokemonModel> findByType(TypeModel type, Pageable pageable);

    List<PokemonModel> findByName(String name);

    Page<PokemonModel> findByName(String name, Pageable pageable);

    void delete(Long id);

    PokemonModel save(PokemonModel pokemon);

    void saveAll(Collection<PokemonModel> pokemons);

    Page<PokemonModel> findByNameAndType(String name, TypeModel type, Pageable pageable);
}
