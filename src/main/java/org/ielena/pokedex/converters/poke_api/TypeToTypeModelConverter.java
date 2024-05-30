package org.ielena.pokedex.converters.poke_api;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.ielena.pokedex.models.TypeModel;
import org.ielena.pokedex.poke_api.Type;
import org.ielena.pokedex.poke_api.side_classes.NamedAPIResource;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TypeToTypeModelConverter implements Converter<Type, TypeModel> {

    public static final Map<Long, TypeModel> cache = new ConcurrentHashMap<>();
    private static final String ASSERTION_FAILED = "[Assertion failed] - typePokeAPI is required; it must not be null";

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
                        .build();
    }
}