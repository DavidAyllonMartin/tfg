package org.ielena.pokedex.converters;

import jakarta.annotation.Resource;
import javafx.scene.image.Image;
import org.ielena.pokedex.dtos.AbilityDto;
import org.ielena.pokedex.dtos.MoveDto;
import org.ielena.pokedex.dtos.PokemonDto;
import org.ielena.pokedex.dtos.TypeDto;
import org.ielena.pokedex.models.AbilityModel;
import org.ielena.pokedex.models.MoveModel;
import org.ielena.pokedex.models.PokemonModel;
import org.ielena.pokedex.models.TypeModel;
import org.ielena.pokedex.services.ImageService;
import org.ielena.pokedex.services.UserService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;
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
    @Resource
    private ImageService defaultImageService;
    @Resource
    private UserService userService;

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

        Image image = null;
        try {
            image = defaultImageService.getImage(pokemonModel.getImgData());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Image thumbnail = null;
        try {
            thumbnail = defaultImageService.getResizedImage(pokemonModel.getImgData(), 110, 110);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        boolean isFavorite = userService.isFavoritePokemon(pokemonModel.getId());

        return PokemonDto.builder()
                         .id(pokemonModel.getId())
                         .name(StringUtils.capitalize(pokemonModel.getName()))
                         .hp(pokemonModel.getHp())
                         .attack(pokemonModel.getAttack())
                         .defense(pokemonModel.getDefense())
                         .specialAttack(pokemonModel.getSpecialAttack())
                         .specialDefense(pokemonModel.getSpecialDefense())
                         .speed(pokemonModel.getSpeed())
                         .baseExperience(pokemonModel.getBaseExperience())
                         .height(BigDecimal.valueOf(pokemonModel.getHeight() / 10.0))
                         .weight(BigDecimal.valueOf(pokemonModel.getWeight() / 10.0))
                         .imgUrl(pokemonModel.getImgUrl())
                         .isDefault(pokemonModel.getIsDefault())
                         .abilities(abilities)
                         .moves(moves)
                         .types(types)
                         .color(pokemonModel.getColor())
                         .img(image)
                         .thumbnail(thumbnail)
                         .description(StringUtils.capitalize(pokemonModel.getDescription()))
                         .isFavorite(isFavorite)
                         .build();
    }
}