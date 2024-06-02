package org.ielena.pokedex.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.Resource;
import org.ielena.pokedex.models.PokemonModel;
import org.ielena.pokedex.poke_api.Pokemon;
import org.ielena.pokedex.poke_api.side_classes.PokeAPIResponse;
import org.ielena.pokedex.services.DatabaseUpdateService;
import org.ielena.pokedex.services.PokemonService;
import org.ielena.pokedex.singletons.CachingObjectMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultDatabaseUpdateService implements DatabaseUpdateService {

    private static final String URL = "https://pokeapi.co/api/v2/pokemon?limit=%d&offset=%d";

    @Resource
    private Converter<Pokemon, PokemonModel> converter;

    @Resource
    private PokemonService pokemonService;

    @Override
    public Long updateDatabase(Integer limit, Integer offset) throws JsonProcessingException {
        Long startTime = System.nanoTime();

        String url = String.format(URL, limit, offset);

        PokeAPIResponse pokeAPIResponse = new CachingObjectMapper().readValue(url, PokeAPIResponse.class);

        List<PokemonModel> pokemonList = pokeAPIResponse.getResults()
                                                        .parallelStream()
                                                        .map(pokemon -> pokemon.createObject(Pokemon.class))
                                                        .map(converter::convert)
                                                        .toList();

        pokemonService.saveAll(pokemonList);

        Long endTime = System.nanoTime();

        return (endTime - startTime);
    }
}
