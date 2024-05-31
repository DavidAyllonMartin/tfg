package org.ielena.pokedex.controllers;

import jakarta.annotation.Resource;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.ielena.pokedex.ProjectJavaFxApp;
import org.ielena.pokedex.dtos.PokemonDto;
import org.ielena.pokedex.facades.PokemonFacade;
import org.ielena.pokedex.singletons.SpringContextSingleton;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MainController {

    @Resource
    private PokemonFacade pokemonFacade;

    private List<PokemonDto> pokemonList = new ArrayList<>();

    private int currentPage = 0;
    private int itemsPerPage = 9; // 3x3 grid, so 9 items per page

    @FXML
    public void initialize() {
//        Task<Void> task = new Task<>() {
//            @Override
//            protected Void call() throws Exception {
//                loadPokemonItems();
//                return null;
//            }
//        };
//
//        Thread thread = new Thread(task);
//        thread.setDaemon(true);
//        thread.start();
        pokemonList = pokemonFacade.findAll();
    }

    @FXML
    public VBox container;

    @FXML
    public GridPane gridPane;

    public void loadPokemonItems() {
        for (PokemonDto pokemon : pokemonFacade.findByName("Pikachu")) {
            try {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(ProjectJavaFxApp.class.getResource("views/pokemon-item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                PokemonItemController itemController = fxmlLoader.getController();
                fxmlLoader.setControllerFactory(SpringContextSingleton.getContext()::getBean);
                itemController.setData(pokemon);
                container.getChildren().add(anchorPane);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadPage(int page) {
        gridPane.getChildren().clear();
        int startIndex = page * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, pokemonList.size());

        for (int i = startIndex; i < endIndex; i++) {
            PokemonDto pokemon = pokemonList.get(i);
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(ProjectJavaFxApp.class.getResource("views/pokemon-item.fxml"));
                AnchorPane vbox = fxmlLoader.load();
                PokemonItemController itemController = fxmlLoader.getController();
                itemController.setData(pokemon);
                gridPane.add(vbox, (i - startIndex) % 3, (i - startIndex) / 3);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void nextPage() {
        if (currentPage < getTotalPages() - 1) {
            currentPage++;
            loadPage(currentPage);
        }
    }

    @FXML
    private void previousPage() {
        if (currentPage > 0) {
            currentPage--;
            loadPage(currentPage);
        }
    }

    private int getTotalPages() {
        return (int) Math.ceil((double) pokemonList.size() / itemsPerPage);
    }

//    private void extracted() throws JsonProcessingException {
//
//        Long startTime = System.nanoTime();
//        System.out.println("Empieza");
//
//        PokeAPIResponse pokeAPIResponse = new CachingObjectMapper().readValue("https://pokeapi.co/api/v2/pokemon?limit=5&offset=20", PokeAPIResponse.class);
//
//        List<PokemonModel> pokemonList = pokeAPIResponse.getResults()
//                                                        .parallelStream()
//                                                        .map(pokemon -> pokemon.createObject(Pokemon.class))
//                                                        .map(converter::convert)
//                                                        .toList();
//
//        System.out.println("Cargado");
//
////        typeService.saveAll(TypeToTypeModelConverter.cache.values());
////        moveService.saveAll(MoveToMoveModelConverter.cache.values());
////        abilityService.saveAll(AbilityToAbilityModelConverter.cache.values());
//        pokemonService.saveAll(pokemonList);
//
//        Long endTime = System.nanoTime();
//
//        System.out.println("Termina en: " + (endTime-startTime)/1000000000);
//    }

}
