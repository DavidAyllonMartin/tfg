package org.ielena.pokedex.controllers;

import jakarta.annotation.Resource;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
import org.ielena.pokedex.services.DatabaseUpdateService;
import org.ielena.pokedex.services.UserSessionService;
import org.ielena.pokedex.singletons.SpringContextSingleton;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.ielena.pokedex.utils.Constants.View.INFO_FXML_PATH;
import static org.ielena.pokedex.utils.Constants.View.LOADING_FXML_PATH;
import static org.ielena.pokedex.utils.Constants.View.LOGIN_FXML_PATH;
import static org.ielena.pokedex.utils.Constants.View.POKEDEX_FXML_PATH;
import static org.ielena.pokedex.utils.Constants.View.REGISTER_FXML_PATH;

@Setter
@Component
public class MasterController implements Mediator, PokedexControllerMediator, PokemonInfoControllerMediator, PokemonItemMediator, LoginControllerMediator, RegisterControllerMediator {

    @Resource
    private UserSessionService userSessionService;
    @Resource
    private DatabaseUpdateService databaseUpdateService;

    private Stage stage;
    private Stage loadingStage;
    private Scene pokedexView;
    private Scene registerView;
    private Scene loginView;

    @SneakyThrows
    @Override
    public void changeToInfoWindow(PokemonDto pokemon) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(ProjectJavaFxApp.class.getResource(INFO_FXML_PATH));
        fxmlLoader.setControllerFactory(SpringContextSingleton.getContext()::getBean);
        Scene scene = new Scene(fxmlLoader.load());
        PokemonInfoController infoController = fxmlLoader.getController();
        infoController.setData(pokemon);
        stage.setScene(scene);
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

    @SneakyThrows
    @Override
    public void updateDatabase() {
        openLoadingWindow();
        Task<Void> task = new Task<>() {
            @SneakyThrows
            @Override
            protected Void call() {

                databaseUpdateService.updateDatabase(251, 0);

                return null;
            }

            @Override
            protected void succeeded() {
                closeLoadingWindow();
                changeToMainWindow();
            }

            @Override
            protected void failed() {
                closeLoadingWindow();
            }
        };

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(task);
        executorService.shutdown();
    }

    @Override
    public void logOut() {
        loadingStage = null;
        pokedexView = null;
        registerView = null;
        loginView = null;

        userSessionService.clear();
        changeToLoginWindow();
    }

    private void centerStage(Stage stage) {
        Rectangle2D screenBounds = Screen.getPrimary()
                                         .getVisualBounds();

        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
    }

    @SneakyThrows
    private void openLoadingWindow() {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(ProjectJavaFxApp.class.getResource(LOADING_FXML_PATH));
        fxmlLoader.setControllerFactory(SpringContextSingleton.getContext()::getBean);
        VBox root = fxmlLoader.load();
        LoadingWindowController loadingWindowController = fxmlLoader.getController();
        databaseUpdateService.registerListener(loadingWindowController);

        loadingStage = new Stage();
        loadingStage.initModality(Modality.APPLICATION_MODAL);
        loadingStage.initStyle(StageStyle.UNDECORATED);

        Scene scene = new Scene(root);
        loadingStage.setScene(scene);
        loadingStage.setOnShown(event -> centerStage(loadingStage));
        loadingStage.show();
    }

    private void closeLoadingWindow() {
        if (loadingStage != null) {
            loadingStage.close();
        }
    }
}