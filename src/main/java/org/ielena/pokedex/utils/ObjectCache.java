package org.ielena.pokedex.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ObjectCache {

    private static final Map<String, Object> pokeApiCache = new ConcurrentHashMap<>();

    public static void addToCache(String key, Object value) {
        pokeApiCache.put(key, value);
    }

    public static Object getFromCache(String key) {
        return pokeApiCache.getOrDefault(key, null);
    }

    public static boolean isInCache(String key) {
        return pokeApiCache.containsKey(key);
    }
}