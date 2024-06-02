package org.ielena.pokedex.converters;

import jakarta.annotation.Resource;
import org.ielena.pokedex.dtos.TypeDto;
import org.ielena.pokedex.models.TypeModel;
import org.ielena.pokedex.services.TypeService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TypeDtoToTypeModelConverter implements Converter<TypeDto, TypeModel> {

    @Resource
    private TypeService typeService;

    @Override
    public TypeModel convert(TypeDto typeDto) {

        return typeService.findById(typeDto.getId())
                          .orElse(null);
    }
}

