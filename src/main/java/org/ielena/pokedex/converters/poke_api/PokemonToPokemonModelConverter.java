package org.ielena.pokedex.converters.poke_api;

import jakarta.annotation.Resource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.ielena.pokedex.models.AbilityModel;
import org.ielena.pokedex.models.MoveModel;
import org.ielena.pokedex.models.PokemonModel;
import org.ielena.pokedex.models.TypeModel;
import org.ielena.pokedex.poke_api.Ability;
import org.ielena.pokedex.poke_api.Move;
import org.ielena.pokedex.poke_api.Pokemon;
import org.ielena.pokedex.poke_api.Type;
import org.ielena.pokedex.poke_api.side_classes.OfficialArtwork;
import org.ielena.pokedex.poke_api.side_classes.Other;
import org.ielena.pokedex.poke_api.side_classes.PokemonAbility;
import org.ielena.pokedex.poke_api.side_classes.PokemonCries;
import org.ielena.pokedex.poke_api.side_classes.PokemonMove;
import org.ielena.pokedex.poke_api.side_classes.PokemonSprites;
import org.ielena.pokedex.poke_api.side_classes.PokemonType;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class PokemonToPokemonModelConverter implements Converter<Pokemon, PokemonModel> {

    public static final Map<Long, PokemonModel> cache = new ConcurrentHashMap<>();
    private static final String ASSERTION_FAILED = "[Assertion failed] - pokemonPokeAPI is required; it must not be null";
    @Resource
    private Converter<Type, TypeModel> typeConverter;
    @Resource
    private Converter<Move, MoveModel> moveConverter;
    @Resource
    private Converter<Ability, AbilityModel> abilityConverter;

    @Override
    public PokemonModel convert(Pokemon pokemon) {
        Assert.notNull(pokemon, ASSERTION_FAILED);

        return cache.computeIfAbsent(pokemon.getId(), key -> getPokemonModel(pokemon));
    }

    private PokemonModel getPokemonModel(Pokemon pokemon) {

        String frontDefaultUrl = Optional.ofNullable(pokemon.getSprites())
                                         .map(PokemonSprites::getOther)
                                         .map(Other::getOfficialArtwork)
                                         .map(OfficialArtwork::getFrontDefault)
                                         .orElse(null);

        String cryUrl = Optional.ofNullable(pokemon.getCries())
                                .map(PokemonCries::getLatest)
                                .orElse(null);

        Set<TypeModel> typeModels = Optional.ofNullable(pokemon.getTypes())
                                            .orElse(Collections.emptyList())
                                            .stream()
                                            .map(PokemonType::getType)
                                            .map(type -> type.createObject(Type.class))
                                            .map(typeConverter::convert)
                                            .collect(Collectors.toSet());

        Set<AbilityModel> abilityModels = Optional.ofNullable(pokemon.getAbilities())
                                                  .orElse(Collections.emptyList())
                                                  .stream()
                                                  .map(PokemonAbility::getAbility)
                                                  .map(ability -> ability.createObject(Ability.class))
                                                  .map(abilityConverter::convert)
                                                  .collect(Collectors.toSet());

        Set<MoveModel> moveModels = Optional.ofNullable(pokemon.getMoves())
                                            .orElse(Collections.emptyList())
                                            .stream()
                                            .map(PokemonMove::getMove)
                                            .map(move -> move.createObject(Move.class))
                                            .map(moveConverter::convert)
                                            .collect(Collectors.toSet());

        return PokemonModel.builder()
                           .id(pokemon.getId())
                           .name(pokemon.getName())
                           .hp(pokemon.getStats()
                                      .get(0)
                                      .getBaseStat())
                           .attack(pokemon.getStats()
                                          .get(1)
                                          .getBaseStat())
                           .defense(pokemon.getStats()
                                           .get(2)
                                           .getBaseStat())
                           .speed(pokemon.getStats()
                                         .get(3)
                                         .getBaseStat())
                           .specialAttack(pokemon.getStats()
                                                 .get(4)
                                                 .getBaseStat())
                           .specialDefense(pokemon.getStats()
                                                  .get(5)
                                                  .getBaseStat())
                           .height(pokemon.getHeight())
                           .weight(pokemon.getWeight())
                           .isDefault(pokemon.getIsDefault())
                           .baseExperience(pokemon.getBaseExperience())
                           .imgUrl(frontDefaultUrl)
                           .cryUrl(cryUrl)
                           .types(typeModels)
                           .abilities(abilityModels)
                           .moves(moveModels)
                           .build();
    }
}