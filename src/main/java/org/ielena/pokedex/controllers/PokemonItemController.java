package org.ielena.pokedex.controllers;

import jakarta.annotation.Resource;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.ielena.pokedex.ProjectJavaFxApp;
import org.ielena.pokedex.controllers.mediator.Mediator;
import org.ielena.pokedex.controllers.mediator.PokemonItemMediator;
import org.ielena.pokedex.dtos.PokemonDto;
import org.ielena.pokedex.dtos.TypeDto;
import org.ielena.pokedex.singletons.MasterControllerSingleton;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Getter
@Setter
@Component
public class PokemonItemController implements ViewController{

    private static final String FX_BACKGROUND = "-fx-background-color: %s";

    public HBox typesContainer;

    private PokemonDto pokemon;

    private PokemonItemMediator mediator;

    @FXML
    private Label name, id;
    @FXML
    private ImageView image;
    @FXML
    private HBox pokemonCard;

    public void initialize(){
        setMediator(MasterControllerSingleton.getInstance());
    }

    public void setData(PokemonDto pokemon) {
        this.pokemon = pokemon;

        id.setText(String.format("#%03d", pokemon.getId()));
        name.setText(pokemon.getName());
        pokemon.getTypes().forEach(this::addType);
        image.setImage(pokemon.getImg());
        pokemonCard.setStyle(String.format(FX_BACKGROUND, pokemon.getColor()));
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = (PokemonItemMediator) mediator;
    }

    public void onPokemonCardClicked(MouseEvent mouseEvent) {
        mediator.changeToInfoWindow(this.pokemon);
    }

    @SneakyThrows
    private void addType(TypeDto typeDto) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(ProjectJavaFxApp.class.getResource("views/type-container.fxml"));
        AnchorPane anchorPane = fxmlLoader.load();
        TypeContainerController typeContainerController = fxmlLoader.getController();
        typeContainerController.setPokemonType(typeDto, 14);
        typesContainer.getChildren().add(anchorPane);
    }
}
