package org.ielena.pokedex.converters;

import jakarta.annotation.Resource;
import org.ielena.pokedex.models.PokemonBasicInfoModel;
import org.ielena.pokedex.models.PokemonModel;
import org.ielena.pokedex.services.PokemonService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PokemonBasicInfoModelToPokemonModelConverter implements Converter<PokemonBasicInfoModel, PokemonModel> {

    @Resource
    private PokemonService pokemonService;

    @Override
    public PokemonModel convert(PokemonBasicInfoModel pokemonBasicInfoModel) {
        return pokemonService.findById(pokemonBasicInfoModel.getId())
                             .orElse(null);
    }
}
