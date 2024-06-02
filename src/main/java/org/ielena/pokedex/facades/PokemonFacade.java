package org.ielena.pokedex.facades;

import javafx.scene.image.Image;
import org.ielena.pokedex.dtos.PokemonDto;
import org.ielena.pokedex.dtos.TypeDto;
import org.ielena.pokedex.models.TypeModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PokemonFacade {

    List<PokemonDto> findAll();

    Page<PokemonDto> findAll(Pageable pageable);

    Optional<PokemonDto> findById(Long id);

    List<PokemonDto> findByType(TypeDto type);

    Page<PokemonDto> findByType(TypeDto type, Pageable pageable);

    List<PokemonDto> findByName(String name);

    Page<PokemonDto> findByName(String name, Pageable pageable);

    Page<PokemonDto> findByNameAndType(String name, TypeDto type, Pageable pageable);
}
