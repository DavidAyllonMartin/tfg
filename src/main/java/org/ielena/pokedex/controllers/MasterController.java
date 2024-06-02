package org.ielena.pokedex.controllers;

import jakarta.annotation.Resource;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Setter;
import lombok.SneakyThrows;
import org.ielena.pokedex.ProjectJavaFxApp;
import org.ielena.pokedex.controllers.mediator.Mediator;
import org.ielena.pokedex.controllers.mediator.PokedexControllerMediator;
import org.ielena.pokedex.controllers.mediator.PokemonInfoControllerMediator;
import org.ielena.pokedex.controllers.mediator.PokemonItemMediator;
import org.ielena.pokedex.dtos.PokemonDto;

@Setter
public class MasterController implements Mediator, PokedexControllerMediator, PokemonInfoControllerMediator, PokemonItemMediator {

    private Stage stage;

    private Scene pokedexView;

    @SneakyThrows
    @Override
    public void changeToInfoWindow(PokemonDto pokemon) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(ProjectJavaFxApp.class.getResource("views/info-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        PokemonInfoController infoController = fxmlLoader.getController();
        infoController.setData(pokemon);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void changeToMainWindow() {
        stage.setScene(pokedexView);
        stage.show();
    }
}
