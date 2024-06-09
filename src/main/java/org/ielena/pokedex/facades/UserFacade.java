package org.ielena.pokedex.facades;

public interface UserFacade {

    boolean addFavoritePokemon(Long pokemonId);

    boolean removeFavoritePokemon(Long pokemonId);
}

