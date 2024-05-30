package org.ielena.pokedex.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.Resource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.ielena.pokedex.converters.poke_api.AbilityToAbilityModelConverter;
import org.ielena.pokedex.converters.poke_api.MoveToMoveModelConverter;
import org.ielena.pokedex.converters.poke_api.PokemonToPokemonModelConverter;
import org.ielena.pokedex.converters.poke_api.TypeToTypeModelConverter;
import org.ielena.pokedex.facades.PokemonFacade;
import org.ielena.pokedex.models.PokemonModel;
import org.ielena.pokedex.poke_api.Pokemon;
import org.ielena.pokedex.poke_api.side_classes.PokeAPIResponse;
import org.ielena.pokedex.services.AbilityService;
import org.ielena.pokedex.services.MoveService;
import org.ielena.pokedex.services.PokemonService;
import org.ielena.pokedex.services.TypeService;
import org.ielena.pokedex.singletons.CachingObjectMapper;

import java.util.List;

@Component
public class MainController {

    @Resource
    private PokemonFacade pokemonFacade;

    @FXML
    private TextField searchBar;
    @FXML
    private ListView<String> listResult;
    @FXML
    private Label nameLabel;
    @FXML///todo
    private Label tiposLabel;
    @FXML
    private Label manaCostLabel;
    @FXML
    private Label oracleLabel;
    @FXML
    private ImageView imagen;

    @Resource
    PokemonService pokemonService;

    @Resource
    Converter<Pokemon, PokemonModel> converter;
    @Resource
    AbilityService abilityService;
    @Resource
    TypeService typeService;
    @Resource
    MoveService moveService;

    @FXML
    private void search(ActionEvent actionEvent) {
//        List<PokemonDto> pokemonDtos = pokemonFacade.findByName("Pikachu");
//        pokemonDtos.stream().map(PokemonDto::getName).forEach(System.out::println);
        try {
            extracted();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void extracted() throws JsonProcessingException {

        Long startTime = System.nanoTime();
        System.out.println("Empieza");

        PokeAPIResponse pokeAPIResponse = new CachingObjectMapper().readValue("https://pokeapi.co/api/v2/pokemon?limit=10000&offset=0", PokeAPIResponse.class);

        List<PokemonModel> pokemonList = pokeAPIResponse.getResults()
                                                        .parallelStream()
                                                        .map(pokemon -> pokemon.createObject(Pokemon.class))
                                                        .map(converter::convert)
                                                        .toList();

        System.out.println("Cargado");

//        typeService.saveAll(TypeToTypeModelConverter.cache.values());
//        moveService.saveAll(MoveToMoveModelConverter.cache.values());
//        abilityService.saveAll(AbilityToAbilityModelConverter.cache.values());
        pokemonService.saveAll(pokemonList);

        Long endTime = System.nanoTime();

        System.out.println("Termina en: " + (endTime-startTime)/1000000000);
    }

    @FXML
    private void selectCard(MouseEvent mouseEvent) {

    }

    @FXML
    private void addCard(ActionEvent actionEvent) {

    }

    @FXML
    private void createCollection(ActionEvent actionEvent) {

    }
}
