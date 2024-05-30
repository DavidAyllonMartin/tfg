package org.ielena.pokedex.facades.impl;

import jakarta.annotation.Resource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.ielena.pokedex.dtos.PokemonDto;
import org.ielena.pokedex.facades.PokemonFacade;
import org.ielena.pokedex.models.PokemonModel;
import org.ielena.pokedex.models.TypeModel;
import org.ielena.pokedex.services.PokemonService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DefaultPokemonFacade implements PokemonFacade {

    @Resource
    private PokemonService pokemonService;
    @Resource
    private Converter<PokemonModel, PokemonDto> pokemonDtoConverter;

    @Override
    public List<PokemonDto> findAll() {

        return pokemonService.findAll()
                             .stream()
                             .map(pokemonDtoConverter::convert)
                             .collect(Collectors.toList());
    }

    @Override
    public Optional<PokemonDto> findById(Long id) {
        return pokemonService.findById(id)
                             .map(pokemonModel -> pokemonDtoConverter.convert(pokemonModel));
    }

    @Override
    public List<PokemonDto> findByType(TypeModel type) {
        return pokemonService.findByType(type)
                             .stream()
                             .map(pokemonDtoConverter::convert)
                             .collect(Collectors.toList());
    }

    @Override
    public List<PokemonDto> findByName(String name) {
        return pokemonService.findByName(name)
                             .stream()
                             .map(pokemonDtoConverter::convert)
                             .collect(Collectors.toList());
    }
}
