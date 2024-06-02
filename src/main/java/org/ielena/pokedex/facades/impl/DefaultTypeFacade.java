package org.ielena.pokedex.facades.impl;

import jakarta.annotation.Resource;
import org.ielena.pokedex.dtos.TypeDto;
import org.ielena.pokedex.facades.TypeFacade;
import org.ielena.pokedex.models.TypeModel;
import org.ielena.pokedex.services.TypeService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DefaultTypeFacade implements TypeFacade {

    @Resource
    private TypeService typeService;
    @Resource
    private Converter<TypeModel, TypeDto> typeDtoConverter;

    @Override
    public List<TypeDto> findAll() {
        return typeService.findAll()
                          .stream()
                          .map(typeDtoConverter::convert)
                          .collect(Collectors.toList());
    }

    @Override
    public Optional<TypeDto> findById(Long id) {
        return typeService.findById(id)
                          .map(typeDtoConverter::convert);
    }
}
