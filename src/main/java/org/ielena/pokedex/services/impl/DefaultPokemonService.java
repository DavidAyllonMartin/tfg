package org.ielena.pokedex.services.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.ielena.pokedex.daos.PokemonDao;
import org.ielena.pokedex.models.PokemonModel;
import org.ielena.pokedex.models.TypeModel;
import org.ielena.pokedex.services.PokemonService;

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
    public Optional<PokemonModel> findById(Long id) {
        return pokemonDao.findById(id);
    }

    @Override
    public List<PokemonModel> findByType(TypeModel type) {
        return pokemonDao.findPokemonModelsByTypesContaining(type);
    }

    @Override
    public List<PokemonModel> findByName(String name) {
        return Optional.ofNullable(name)
                       .map(pokemonDao::findPokemonModelsByNameContainingIgnoreCase)
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
}
