package org.ielena.pokedex.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.ielena.pokedex.ProjectJavaFxApp;
import org.ielena.pokedex.controllers.mediator.Mediator;
import org.ielena.pokedex.controllers.mediator.PokemonItemMediator;
import org.ielena.pokedex.dtos.PokemonDto;
import org.ielena.pokedex.dtos.TypeDto;
import org.ielena.pokedex.singletons.MasterControllerSingleton;
import org.ielena.pokedex.singletons.SpringContextSingleton;
import org.springframework.stereotype.Component;

import static org.ielena.pokedex.utils.Constants.View.TYPE_ITEM_FXML_PATH;

@Getter
@Setter
@Component
public class PokemonItemController implements ViewController {

    private static final String FX_BACKGROUND = "-fx-background-color: %s";

    @FXML
    private Label id;
    @FXML
    private Label name;
    @FXML
    private ImageView image;
    @FXML
    private AnchorPane pokemonCard;
    @FXML
    private VBox typesContainer;

    private PokemonItemMediator mediator;
    private PokemonDto pokemon;

    public void initialize() {
        setMediator(MasterControllerSingleton.getInstance());
    }

    public void setData(PokemonDto pokemon) {
        this.pokemon = pokemon;

        id.setText(String.format("#%03d", pokemon.getId()));
        name.setText(pokemon.getName());
        pokemon.getTypes()
               .forEach(this::addType);
        image.setImage(pokemon.getThumbnail());
        pokemonCard.setStyle(String.format(FX_BACKGROUND, pokemon.getColor()));
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = (PokemonItemMediator) mediator;
    }

    @FXML
    public void onPokemonCardClicked(MouseEvent mouseEvent) {
        mediator.changeToInfoWindow(this.pokemon);
    }

    @SneakyThrows
    private void addType(TypeDto typeDto) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(ProjectJavaFxApp.class.getResource(TYPE_ITEM_FXML_PATH));
        fxmlLoader.setControllerFactory(SpringContextSingleton.getContext()::getBean);
        AnchorPane anchorPane = fxmlLoader.load();
        TypeItemController typeItemController = fxmlLoader.getController();
        typeItemController.setPokemonType(typeDto, 14);
        typesContainer.getChildren()
                      .add(anchorPane);
    }
}