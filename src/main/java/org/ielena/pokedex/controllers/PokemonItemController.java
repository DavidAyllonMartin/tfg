package org.ielena.pokedex.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;
import org.ielena.pokedex.ProjectJavaFxApp;
import org.ielena.pokedex.dtos.PokemonDto;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Getter
@Setter
@Component
public class PokemonItemController {

    private PokemonDto pokemon;

    @FXML
    private Label name, id, type1, type2;
    @FXML
    private ImageView image;
    @FXML
    private HBox pokemonCard;

    @FXML
    private void click1(MouseEvent mouseEvent) {
        changeToInfoWindow(this.pokemon);
    }

    public void setData(PokemonDto pokemon) {
        this.pokemon = pokemon;

        id.setText(String.format("#%03d", pokemon.getId()));
        name.setText(pokemon.getName());
        type1.setText(pokemon.getTypes().get(0).getName());
//        type2.setText(pokemon.getType2());
        Image value = new Image(pokemon.getImgUrl());
        image.setImage(value);
        image.setSmooth(true);
        image.setCache(true);

//        setBackgroundColor(pokemon);
    }

//    private void setBackgroundColor(Pokemon pokemon) {
//        Color dominantColor = ColorAnalyzer.getDominantColor(pokemon.getImage());
//        String backgroundColor = String.format("-fx-background-color: rgba(%d, %d, %d, 1.0);",
//                (int) (dominantColor.getRed() * 255),
//                (int) (dominantColor.getGreen() * 255),
//                (int) (dominantColor.getBlue() * 255));
//        pokemonCard.setStyle(backgroundColor);
//    }

    private void changeToInfoWindow(PokemonDto pokemon) {
        System.out.println("Pokemon " + pokemon.getName());
    }
}
