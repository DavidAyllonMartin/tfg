package org.ielena.pokedex.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.Resource;
import org.ielena.pokedex.controllers.DatabaseUpdateListener;
import org.ielena.pokedex.models.PokemonModel;
import org.ielena.pokedex.poke_api.Pokemon;
import org.ielena.pokedex.poke_api.side_classes.PokeAPIResponse;
import org.ielena.pokedex.services.DatabaseUpdateService;
import org.ielena.pokedex.services.PokemonService;
import org.ielena.pokedex.utils.CachingObjectMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class DefaultDatabaseUpdateService implements DatabaseUpdateService {

    private static final String URL = "https://pokeapi.co/api/v2/pokemon?limit=%d&offset=%d";

    @Resource
    private Converter<Pokemon, PokemonModel> converter;
    @Resource
    private PokemonService pokemonService;

    private final Set<DatabaseUpdateListener> listeners = new HashSet<>();

    public void registerListener(DatabaseUpdateListener listener) {
        listeners.add(listener);
    }

    public void unregisterListener(DatabaseUpdateListener listener) {
        listeners.remove(listener);
    }

    @Override
    public Long updateDatabase(Integer limit, Integer offset) throws JsonProcessingException {
        Long startTime = System.nanoTime();

        String url = String.format(URL, limit, offset);

        PokeAPIResponse pokeAPIResponse = new CachingObjectMapper().readValue(url, PokeAPIResponse.class);

        int total = limit - offset;
        AtomicInteger processed = new AtomicInteger();

        List<PokemonModel> pokemonList = pokeAPIResponse.getResults()
                                                        .parallelStream()
                                                        .map(pokemon -> {
                                                            PokemonModel model = converter.convert(pokemon.createObject(Pokemon.class));
                                                            notifyListeners(processed.getAndIncrement(), total);
                                                            return model;
                                                        })
                                                        .toList();

        pokemonService.saveAll(pokemonList);

        Long endTime = System.nanoTime();

        return (endTime - startTime);
    }

    private void notifyListeners(int processed, int total) {
        for (DatabaseUpdateListener listener : listeners) {
            listener.onPokemonGenerated(processed, total);
        }
    }
}