package org.ielena.pokedex.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.ielena.pokedex.dtos.AbilityDto;
import org.ielena.pokedex.models.AbilityModel;

@Component
public class AbilityModelToAbilityDtoConverter implements Converter<AbilityModel, AbilityDto> {


    @Override
    public AbilityDto convert(AbilityModel abilityModel) {

        return AbilityDto.builder()
                         .id(abilityModel.getId())
                         .name(abilityModel.getName())
                         .effect(abilityModel.getFlavorText())
                         .build();
    }
}
