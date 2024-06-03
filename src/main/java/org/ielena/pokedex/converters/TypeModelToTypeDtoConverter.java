package org.ielena.pokedex.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.ielena.pokedex.dtos.TypeDto;
import org.ielena.pokedex.models.TypeModel;
import org.springframework.util.StringUtils;

@Component
public class TypeModelToTypeDtoConverter implements Converter<TypeModel, TypeDto> {

    @Override
    public TypeDto convert(TypeModel typeModel) {

        return TypeDto.builder()
                      .id(typeModel.getId())
                      .name(StringUtils.capitalize(typeModel.getName()))
                      .color(typeModel.getColor())
                      .build();
    }
}
