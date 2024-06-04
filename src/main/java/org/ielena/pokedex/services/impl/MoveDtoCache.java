package org.ielena.pokedex.services.impl;

import javafx.scene.Node;
import org.ielena.pokedex.dtos.MoveDto;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MoveDtoCache {

    private final Map<MoveDto, Node> moveDtoNodeCache = new ConcurrentHashMap<>();

    public void addMoveDtoNodeToCache(MoveDto key, Node value) {
        moveDtoNodeCache.put(key, value);
    }

    public Node getMoveDtoNodeFromCache(MoveDto key) {
        return moveDtoNodeCache.getOrDefault(key, null);
    }

    public boolean isMoveDtoNodeInCache(MoveDto key) {
        return moveDtoNodeCache.containsKey(key);
    }
}
