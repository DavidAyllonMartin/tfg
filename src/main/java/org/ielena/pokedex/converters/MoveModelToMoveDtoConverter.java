package org.ielena.pokedex.converters;

import jakarta.annotation.Resource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.ielena.pokedex.dtos.MoveDto;
import org.ielena.pokedex.dtos.TypeDto;
import org.ielena.pokedex.models.MoveModel;
import org.ielena.pokedex.models.TypeModel;

@Component
public class MoveModelToMoveDtoConverter implements Converter<MoveModel, MoveDto> {

    @Resource
    private Converter<TypeModel, TypeDto> typeDtoConverter;

    @Override
    public MoveDto convert(MoveModel moveModel) {

        return MoveDto.builder()
                      .id(moveModel.getId())
                      .name(moveModel.getName())
                      .pp(moveModel.getPp())
                      .accuracy(moveModel.getAccuracy())
                      .type(typeDtoConverter.convert(moveModel.getType()))
                      .power(moveModel.getPower())
                      .priority(moveModel.getPriority())
                      .effectChance(moveModel.getEffectChance())
                      .flavorText(moveModel.getFlavorText())
                      .build();
    }
}
