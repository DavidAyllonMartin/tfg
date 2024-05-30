package org.ielena.pokedex.converters.poke_api;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.ielena.pokedex.models.AbilityModel;
import org.ielena.pokedex.poke_api.Ability;
import org.ielena.pokedex.poke_api.side_classes.FlavorText;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AbilityToAbilityModelConverter implements Converter<Ability, AbilityModel> {

    public static final Map<Long, AbilityModel> cache = new ConcurrentHashMap<>();
    private static final String ASSERTION_FAILED = "[Assertion failed] - abilityPokeAPI is required; it must not be null";

    @Override
    public AbilityModel convert(Ability ability) {
        Assert.notNull(ability, ASSERTION_FAILED);

        return cache.computeIfAbsent(ability.getId(), id -> getAbilityModel(ability));
    }

    private AbilityModel getAbilityModel(Ability ability) {
        String flavorText = ability.getFlavorTextEntries()
                                   .stream()
                                   .filter(text -> "en".equals(text.getLanguage()
                                                                   .getName()))
                                   .map(FlavorText::getFlavorText)
                                   .findFirst()
                                   .orElse("");

        return AbilityModel.builder()
                           .id(ability.getId())
                           .name(ability.getName())
                           .isMainSeries(ability.getIsMainSeries())
                           .generation(ability.getGeneration()
                                              .getName())
                           .flavorText(flavorText)
                           .build();
    }
}