package org.ielena.pokedex.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Setter;
import lombok.SneakyThrows;
import org.ielena.pokedex.ProjectJavaFxApp;
import org.ielena.pokedex.controllers.mediator.LoginControllerMediator;
import org.ielena.pokedex.controllers.mediator.Mediator;
import org.ielena.pokedex.controllers.mediator.PokedexControllerMediator;
import org.ielena.pokedex.controllers.mediator.PokemonInfoControllerMediator;
import org.ielena.pokedex.controllers.mediator.PokemonItemMediator;
import org.ielena.pokedex.controllers.mediator.RegisterControllerMediator;
import org.ielena.pokedex.dtos.PokemonDto;
import org.ielena.pokedex.singletons.SpringContextSingleton;

@Setter
public class MasterController implements Mediator, PokedexControllerMediator, PokemonInfoControllerMediator, PokemonItemMediator, LoginControllerMediator, RegisterControllerMediator {

    private static final String VIEW_FXML = "views/info-view.fxml";
    private static final String POKEDEX_FXML = "views/pokedex-view.fxml";
    private static final String REGISTER_FXML = "views/register-view.fxml";
    private static final String LOGIN_FXML = "views/login-view.fxml";

    private Stage stage;
    private Scene pokedexView;

    @SneakyThrows
    @Override
    public void changeToInfoWindow(PokemonDto pokemon) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(ProjectJavaFxApp.class.getResource(VIEW_FXML));
        fxmlLoader.setControllerFactory(SpringContextSingleton.getContext()::getBean);
        Scene scene = new Scene(fxmlLoader.load());
        PokemonInfoController infoController = fxmlLoader.getController();
        infoController.setData(pokemon);
        stage.setScene(scene);
        stage.show();
    }

    @SneakyThrows
    @Override
    public void changeToMainWindow() {
        stage.setScene(pokedexView);
        stage.show();
    }

    @SneakyThrows
    @Override
    public void changeToRegisterWindow() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(ProjectJavaFxApp.class.getResource(REGISTER_FXML));
        fxmlLoader.setControllerFactory(SpringContextSingleton.getContext()::getBean);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    @SneakyThrows
    @Override
    public void changeToLoginWindow() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(ProjectJavaFxApp.class.getResource(LOGIN_FXML));
        fxmlLoader.setControllerFactory(SpringContextSingleton.getContext()::getBean);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }
}
