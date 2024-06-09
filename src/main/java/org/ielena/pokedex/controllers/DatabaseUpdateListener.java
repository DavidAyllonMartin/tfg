package org.ielena.pokedex.controllers;

public interface DatabaseUpdateListener {
    void onPokemonGenerated(int processed, int total);
}