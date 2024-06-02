package org.ielena.pokedex.facades;

import org.ielena.pokedex.dtos.TypeDto;

import java.util.List;
import java.util.Optional;

public interface TypeFacade {

    List<TypeDto> findAll();

    Optional<TypeDto> findById(Long id);
}
