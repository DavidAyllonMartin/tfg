package org.ielena.pokedex.daos;

import org.ielena.pokedex.models.PokemonModel;
import org.ielena.pokedex.models.TypeModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PokemonDao extends JpaRepository<PokemonModel, Long> {

    List<PokemonModel> findPokemonModelsByTypesContaining(TypeModel type);

    List<PokemonModel> findPokemonModelsByNameContainingIgnoreCase(String name);

    Page<PokemonModel> findPokemonModelsByTypesContaining(TypeModel type, Pageable pageable);

    Page<PokemonModel> findPokemonModelsByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<PokemonModel> findPokemonModelsByNameContainingIgnoreCaseAndTypesContaining(String name, TypeModel type, Pageable pageable);
}
