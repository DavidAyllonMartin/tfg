package org.ielena.pokedex.converters;

import jakarta.annotation.Resource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.ielena.pokedex.dtos.AbilityDto;
import org.ielena.pokedex.dtos.MoveDto;
import org.ielena.pokedex.dtos.PokemonDto;
import org.ielena.pokedex.dtos.TypeDto;
import org.ielena.pokedex.models.AbilityModel;
import org.ielena.pokedex.models.MoveModel;
import org.ielena.pokedex.models.PokemonModel;
import org.ielena.pokedex.models.TypeModel;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PokemonModelToPokemonDtoConverter implements Converter<PokemonModel, PokemonDto> {

    @Resource
    private Converter<MoveModel, MoveDto> moveDtoConverter;
    @Resource
    private Converter<AbilityModel, AbilityDto> abilityDtoConverter;
    @Resource
    private Converter<TypeModel, TypeDto> typeDtoConverter;

    @Override
    public PokemonDto convert(PokemonModel pokemonModel) {

        List<MoveDto> moves = Optional.of(pokemonModel)
                                      .map(PokemonModel::getMoves)
                                      .orElse(Collections.emptySet())
                                      .stream()
                                      .map(moveDtoConverter::convert)
                                      .collect(Collectors.toList());

        List<AbilityDto> abilities = Optional.of(pokemonModel)
                                             .map(PokemonModel::getAbilities)
                                             .orElse(Collections.emptySet())
                                             .stream()
                                             .map(abilityDtoConverter::convert)
                                             .collect(Collectors.toList());

        List<TypeDto> types = Optional.of(pokemonModel)
                                      .map(PokemonModel::getTypes)
                                      .orElse(Collections.emptySet())
                                      .stream()
                                      .map(typeDtoConverter::convert)
                                      .collect(Collectors.toList());

        return PokemonDto.builder()
                         .id(pokemonModel.getId())
                         .name(pokemonModel.getName())
                         .hp(pokemonModel.getHp())
                         .attack(pokemonModel.getAttack())
                         .defense(pokemonModel.getDefense())
                         .specialAttack(pokemonModel.getSpecialAttack())
                         .specialDefense(pokemonModel.getSpecialDefense())
                         .speed(pokemonModel.getSpeed())
                         .baseExperience(pokemonModel.getBaseExperience())
                         .height(pokemonModel.getHeight())
                         .weight(pokemonModel.getWeight())
                         .cryUrl(pokemonModel.getCryUrl())
                         .imgUrl(pokemonModel.getImgUrl())
                         .isDefault(pokemonModel.getIsDefault())
                         .abilities(abilities)
                         .moves(moves)
                         .types(types)
                         .build();
    }
}
