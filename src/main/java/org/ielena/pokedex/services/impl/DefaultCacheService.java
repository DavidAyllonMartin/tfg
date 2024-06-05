package org.ielena.pokedex.services.impl;

import javafx.scene.Node;
import org.ielena.pokedex.dtos.MoveDto;
import org.ielena.pokedex.dtos.PokemonDto;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DefaultCacheService {

    private final Map<MoveDto, Node> moveDtoNodeCache = new ConcurrentHashMap<>();
    private final Map<PokemonDto, Node> pokemonDtoNodeCache = new ConcurrentHashMap<>();

    public void addMoveDtoNodeToCache(MoveDto key, Node value) {
        moveDtoNodeCache.put(key, value);
    }

    public Node getMoveDtoNodeFromCache(MoveDto key) {
        return moveDtoNodeCache.getOrDefault(key, null);
    }

    public boolean isMoveDtoNodeInCache(MoveDto key) {
        return moveDtoNodeCache.containsKey(key);
    }

    public void addPokemonDtoNodeToCache(PokemonDto key, Node value) {
        pokemonDtoNodeCache.put(key, value);
    }

    public Node getPokemonDtoNodeFromCache(PokemonDto key) {
        return pokemonDtoNodeCache.getOrDefault(key, null);
    }

    public boolean isPokemonDtoNodeInCache(PokemonDto key) {
        return pokemonDtoNodeCache.containsKey(key);
    }
}
