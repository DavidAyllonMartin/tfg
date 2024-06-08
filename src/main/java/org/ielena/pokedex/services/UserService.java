package org.ielena.pokedex.services;

import org.ielena.pokedex.models.PokemonModel;
import org.ielena.pokedex.models.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    void register(UserModel user);

    UserModel findByUsername(String username);

    boolean addFavoritePokemon(Long pokemonId);

    boolean removeFavoritePokemon(Long pokemonId);

    boolean isFavoritePokemon(Long pokemonId);

    Page<PokemonModel> findUserFavorites(Pageable pageable);
}
