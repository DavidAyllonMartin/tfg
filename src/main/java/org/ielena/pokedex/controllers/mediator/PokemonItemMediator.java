package org.ielena.pokedex.controllers.mediator;

import org.ielena.pokedex.dtos.PokemonDto;

public interface PokemonItemMediator {

    void changeToInfoWindow(PokemonDto pokemonDto);
}
