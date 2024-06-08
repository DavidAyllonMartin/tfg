package org.ielena.pokedex.facades.impl;

import jakarta.annotation.Resource;
import org.ielena.pokedex.facades.UserFacade;
import org.ielena.pokedex.services.UserService;
import org.springframework.stereotype.Component;

@Component
public class DefaultUserFacade implements UserFacade {

    @Resource
    private UserService userService;

    @Override
    public boolean addFavoritePokemon(Long pokemonId) {
        return userService.addFavoritePokemon(pokemonId);
    }

    @Override
    public boolean removeFavoritePokemon(Long pokemonId) {
        return userService.removeFavoritePokemon(pokemonId);
    }
}

