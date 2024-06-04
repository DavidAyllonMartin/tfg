package org.ielena.pokedex.services.impl;

import jakarta.annotation.Resource;
import org.ielena.pokedex.daos.PokemonDao;
import org.ielena.pokedex.models.PokemonModel;
import org.ielena.pokedex.models.TypeModel;
import org.ielena.pokedex.services.PokemonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultPokemonService implements PokemonService {

    @Resource
    private PokemonDao pokemonDao;

    @Override
    public List<PokemonModel> findAll() {
        return pokemonDao.findAll();
    }

    @Override
    public Page<PokemonModel> findAll(Pageable pageable) {
        return Optional.ofNullable(pageable)
                       .map(pokemonDao::findAll)
                       .orElseThrow();
    }

    @Override
    public Optional<PokemonModel> findById(Long id) {
        return pokemonDao.findById(id);
    }

    @Override
    public List<PokemonModel> findByType(TypeModel type) {
        return pokemonDao.findPokemonModelsByTypesContaining(type);
    }

    @Override
    public Page<PokemonModel> findByType(TypeModel type, Pageable pageable) {
        return Optional.ofNullable(type)
                       .flatMap(t -> Optional.ofNullable(pageable)
                                             .map(p -> pokemonDao.findPokemonModelsByTypesContaining(t, p)))
                       .orElseThrow();
    }

    @Override
    public List<PokemonModel> findByName(String name) {
        return Optional.ofNullable(name)
                       .map(pokemonDao::findPokemonModelsByNameContainingIgnoreCase)
                       .orElseThrow();
    }

    @Override
    public Page<PokemonModel> findByName(String name, Pageable pageable) {
        return Optional.ofNullable(name)
                       .flatMap(n -> Optional.ofNullable(pageable)
                                             .map(p -> pokemonDao.findPokemonModelsByNameContainingIgnoreCase(n, p)))
                       .orElseThrow();
    }

    @Override
    public void delete(Long id) {
        Optional.ofNullable(id)
                .ifPresent(pokemonDao::deleteById);
    }

    @Override
    public PokemonModel save(PokemonModel pokemon) {
        return Optional.ofNullable(pokemon)
                       .map(pokemonDao::save)
                       .orElseThrow();
    }

    @Override
    public void saveAll(Collection<PokemonModel> pokemons) {
        pokemonDao.saveAll(pokemons);
    }

    @Override
    public Page<PokemonModel> findByNameAndType(String name, TypeModel type, Pageable pageable) {
        return pokemonDao.findPokemonModelsByNameContainingIgnoreCaseAndTypesContaining(name, type, pageable);
    }
}