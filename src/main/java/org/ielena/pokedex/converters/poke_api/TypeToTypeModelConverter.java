package org.ielena.pokedex.converters.poke_api;

import org.ielena.pokedex.models.TypeModel;
import org.ielena.pokedex.poke_api.Type;
import org.ielena.pokedex.poke_api.side_classes.NamedAPIResource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TypeToTypeModelConverter implements Converter<Type, TypeModel> {

    private static final Map<Long, TypeModel> cache = new ConcurrentHashMap<>();
    private static final String ASSERTION_FAILED = "[Assertion failed] - typePokeAPI is required; it must not be null";

    private static final Map<Long, String> TYPE_COLORS = new HashMap<>();

    static {
        TYPE_COLORS.put(1L, "#A8A77A"); //normal
        TYPE_COLORS.put(2L, "#C22E28"); //fighting
        TYPE_COLORS.put(3L, "#A98FF3"); //flying
        TYPE_COLORS.put(4L, "#A33EA1"); //poison
        TYPE_COLORS.put(5L, "#E2BF65"); //ground
        TYPE_COLORS.put(6L, "#B6A136"); //rock
        TYPE_COLORS.put(7L, "#A6B91A"); //bug
        TYPE_COLORS.put(8L, "#735797"); //ghost
        TYPE_COLORS.put(9L, "#B7B7CE"); //steel
        TYPE_COLORS.put(10L, "#EE8130"); //fire
        TYPE_COLORS.put(11L, "#6390F0"); //water
        TYPE_COLORS.put(12L, "#7AC74C"); //grass
        TYPE_COLORS.put(13L, "#F7D02C"); //electric
        TYPE_COLORS.put(14L, "#F95587"); //psychic
        TYPE_COLORS.put(15L, "#96D9D6"); //ice
        TYPE_COLORS.put(16L, "#6F35FC"); //dragon
        TYPE_COLORS.put(17L, "#705746"); //dark
        TYPE_COLORS.put(18L, "#D685AD"); //fairy
    }

    @Override
    public TypeModel convert(Type type) {
        Assert.notNull(type, ASSERTION_FAILED);

        return cache.computeIfAbsent(type.getId(), key -> getTypeModel(type));
    }

    private TypeModel getTypeModel(Type type) {

        return TypeModel.builder()
                        .id(type.getId())
                        .name(type.getName())
                        .generation(type.getGeneration()
                                        .getName())
                        .moveDamageClass(Optional.ofNullable(type.getMoveDamageClass())
                                                 .map(NamedAPIResource::getName)
                                                 .orElse(""))
                        .color(getColorForType(type.getId()))
                        .build();
    }

    private String getColorForType(Long typeId) {
        return TYPE_COLORS.getOrDefault(typeId, "#FFFFFF"); // Default color if type is not recognized
    }
}