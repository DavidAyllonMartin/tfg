package org.ielena.pokedex.controllers;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
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

import static org.ielena.pokedex.utils.Constants.View.INFO_FXML_PATH;
import static org.ielena.pokedex.utils.Constants.View.LOGIN_FXML_PATH;
import static org.ielena.pokedex.utils.Constants.View.POKEDEX_FXML_PATH;
import static org.ielena.pokedex.utils.Constants.View.REGISTER_FXML_PATH;

@Setter
public class MasterController implements Mediator, PokedexControllerMediator, PokemonInfoControllerMediator, PokemonItemMediator, LoginControllerMediator, RegisterControllerMediator {

    private Stage stage;
    private Scene pokedexView;
    private Scene infoView;
    private Scene registerView;
    private Scene loginView;

    @SneakyThrows
    @Override
    public void changeToInfoWindow(PokemonDto pokemon) {
        if (infoView == null) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(ProjectJavaFxApp.class.getResource(INFO_FXML_PATH));
            fxmlLoader.setControllerFactory(SpringContextSingleton.getContext()::getBean);
            infoView = new Scene(fxmlLoader.load());
            PokemonInfoController infoController = fxmlLoader.getController();
            infoController.setData(pokemon);
            infoView.setUserData(infoController);
        } else {
            PokemonInfoController infoController = (PokemonInfoController) infoView.getUserData();
            infoController.setData(pokemon);
        }
        stage.setScene(infoView);
        stage.show();
        centerStage(stage);
    }

    @SneakyThrows
    @Override
    public void changeToMainWindow() {
        if (pokedexView == null) {
            FXMLLoader pokedexLoader = new FXMLLoader();
            pokedexLoader.setLocation(ProjectJavaFxApp.class.getResource(POKEDEX_FXML_PATH));
            pokedexLoader.setControllerFactory(SpringContextSingleton.getContext()::getBean);
            pokedexView = new Scene(pokedexLoader.load());
            PokedexController pokedexController = pokedexLoader.getController();
            pokedexController.executeLoad();
            pokedexView.setUserData(pokedexController);
        } else {
            PokedexController pokedexController = (PokedexController) pokedexView.getUserData();
            pokedexController.executeLoad();
        }
        stage.setScene(pokedexView);
        stage.show();
        centerStage(stage);
    }

    @SneakyThrows
    @Override
    public void changeToRegisterWindow() {
        if (registerView == null) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(ProjectJavaFxApp.class.getResource(REGISTER_FXML_PATH));
            fxmlLoader.setControllerFactory(SpringContextSingleton.getContext()::getBean);
            registerView = new Scene(fxmlLoader.load());
        }
        stage.setScene(registerView);
        stage.show();
        centerStage(stage);
    }

    @SneakyThrows
    @Override
    public void changeToLoginWindow() {
        if (loginView == null) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(ProjectJavaFxApp.class.getResource(LOGIN_FXML_PATH));
            fxmlLoader.setControllerFactory(SpringContextSingleton.getContext()::getBean);
            loginView = new Scene(fxmlLoader.load());
        }
        stage.setScene(loginView);
        stage.show();
        centerStage(stage);
    }

    private void centerStage(Stage stage) {
        Rectangle2D screenBounds = Screen.getPrimary()
                                         .getVisualBounds();

        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
    }

}
