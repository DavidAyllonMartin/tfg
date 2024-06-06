package org.ielena.pokedex.facades.impl;

import jakarta.annotation.Resource;
import org.ielena.pokedex.dtos.PokemonDto;
import org.ielena.pokedex.dtos.TypeDto;
import org.ielena.pokedex.facades.PokemonFacade;
import org.ielena.pokedex.models.PokemonModel;
import org.ielena.pokedex.models.TypeModel;
import org.ielena.pokedex.services.PokemonService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DefaultPokemonFacade implements PokemonFacade {

    @Resource
    private PokemonService pokemonService;
    @Resource
    private Converter<TypeDto, TypeModel> typeModelConverter;
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
    public Page<PokemonDto> findAll(Pageable pageable) {
        return pokemonService.findAll(pageable)
                             .map(pokemonDtoConverter::convert);
    }

    @Override
    public Optional<PokemonDto> findById(Long id) {
        return pokemonService.findById(id)
                             .map(pokemonModel -> pokemonDtoConverter.convert(pokemonModel));
    }

    @Override
    public List<PokemonDto> findByType(TypeDto type) {

        return pokemonService.findByType(typeModelConverter.convert(type))
                             .stream()
                             .map(pokemonDtoConverter::convert)
                             .collect(Collectors.toList());
    }

    @Override
    public Page<PokemonDto> findByType(TypeDto type, Pageable pageable) {
        return pokemonService.findByType(typeModelConverter.convert(type), pageable)
                             .map(pokemonDtoConverter::convert);
    }

    @Override
    public List<PokemonDto> findByName(String name) {
        return pokemonService.findByName(name)
                             .stream()
                             .map(pokemonDtoConverter::convert)
                             .collect(Collectors.toList());
    }

    @Override
    public Page<PokemonDto> findByName(String name, Pageable pageable) {
        return pokemonService.findByName(name, pageable)
                             .map(pokemonDtoConverter::convert);
    }

    @Override
    public Page<PokemonDto> findByNameAndType(String name, TypeDto type, Pageable pageable) {
        return pokemonService.findByNameAndType(name, typeModelConverter.convert(type), pageable)
                             .map(pokemonDtoConverter::convert);
    }
}