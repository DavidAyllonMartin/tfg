package org.ielena.pokedex.converters;

import jakarta.annotation.Resource;
import org.ielena.pokedex.dtos.MoveDto;
import org.ielena.pokedex.dtos.TypeDto;
import org.ielena.pokedex.models.MoveModel;
import org.ielena.pokedex.models.TypeModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Component
public class MoveModelToMoveDtoConverter implements Converter<MoveModel, MoveDto> {

    public static final String NULL_VALUE_REPLACE = "-";
    @Resource
    private Converter<TypeModel, TypeDto> typeDtoConverter;

    @Override
    public MoveDto convert(MoveModel moveModel) {

        return MoveDto.builder()
                      .id(moveModel.getId())
                      .name(StringUtils.capitalize(moveModel.getName()))
                      .pp(Optional.ofNullable(moveModel.getPp())
                                  .map(String::valueOf)
                                  .orElse(NULL_VALUE_REPLACE))
                      .accuracy(Optional.ofNullable(moveModel.getAccuracy())
                                        .map(String::valueOf)
                                        .orElse(NULL_VALUE_REPLACE))
                      .type(typeDtoConverter.convert(moveModel.getType()))
                      .power(Optional.ofNullable(moveModel.getPower())
                                     .map(String::valueOf)
                                     .orElse(NULL_VALUE_REPLACE))
                      .priority(Optional.ofNullable(moveModel.getPriority())
                                        .map(String::valueOf)
                                        .orElse(NULL_VALUE_REPLACE))
                      .effectChance(Optional.ofNullable(moveModel.getEffectChance())
                                            .map(String::valueOf)
                                            .orElse(NULL_VALUE_REPLACE))
                      .flavorText(StringUtils.capitalize(moveModel.getFlavorText()))
                      .damageClass(StringUtils.capitalize(moveModel.getMoveDamageClass()))
                      .build();
    }
}
