package org.ielena.pokedex.converters.poke_api;

import jakarta.annotation.Resource;
import org.ielena.pokedex.models.MoveModel;
import org.ielena.pokedex.models.TypeModel;
import org.ielena.pokedex.poke_api.Move;
import org.ielena.pokedex.poke_api.Type;
import org.ielena.pokedex.poke_api.side_classes.FlavorText;
import org.ielena.pokedex.poke_api.side_classes.NamedAPIResource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MoveToMoveModelConverter implements Converter<Move, MoveModel> {

    public static final Map<Long, MoveModel> cache = new ConcurrentHashMap<>();
    private static final String ASSERTION_FAILED = "[Assertion failed] - movePokeAPI is required; it must not be null";

    @Resource
    private Converter<Type, TypeModel> typeModelConverter;

    @Override
    public MoveModel convert(Move move) {
        Assert.notNull(move, ASSERTION_FAILED);

        return cache.computeIfAbsent(move.getId(), key -> getMoveModel(move));
    }

    private MoveModel getMoveModel(Move move) {
        String flavorText = Optional.ofNullable(move.getFlavorTextEntries())
                                    .orElse(Collections.emptyList())
                                    .stream()
                                    .filter(text -> "en".equals(text.getLanguage()
                                                                    .getName()))
                                    .map(FlavorText::getFlavorText)
                                    .findFirst()
                                    .orElse("");

        TypeModel typeModel = Optional.ofNullable(move.getType())
                                      .map(type -> type.createObject(Type.class))
                                      .map(typeModelConverter::convert)
                                      .orElse(null);

        return MoveModel.builder()
                        .id(move.getId())
                        .name(move.getName())
                        .accuracy(move.getAccuracy())
                        .effectChance(move.getEffectChance())
                        .pp(move.getPp())
                        .priority(move.getPriority())
                        .power(move.getPower())
                        .moveDamageClass(Optional.ofNullable(move.getDamageClass())
                                                 .map(NamedAPIResource::getName)
                                                 .orElse(""))
                        .generation(Optional.ofNullable(move.getGeneration())
                                            .map(NamedAPIResource::getName)
                                            .orElse(""))
                        .type(typeModel)
                        .flavorText(flavorText)
                        .build();
    }
}