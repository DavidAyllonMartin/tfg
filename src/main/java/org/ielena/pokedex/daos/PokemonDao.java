package org.ielena.pokedex.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ielena.pokedex.models.PokemonModel;
import org.ielena.pokedex.models.TypeModel;

import java.util.List;

@Repository
public interface PokemonDao extends JpaRepository<PokemonModel, Long> {

    List<PokemonModel> findPokemonModelsByTypesContaining(TypeModel type);

    List<PokemonModel> findPokemonModelsByNameContainingIgnoreCase(String name);
}
