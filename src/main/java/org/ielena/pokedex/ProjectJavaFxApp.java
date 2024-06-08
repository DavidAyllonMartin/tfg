package org.ielena.pokedex;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.ielena.pokedex.controllers.MasterController;
import org.ielena.pokedex.singletons.MasterControllerSingleton;
import org.ielena.pokedex.singletons.SpringContextSingleton;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
public class ProjectJavaFxApp extends Application {

    private static final String APP_TITLE = "Pokedex";
//    public static final String VIEW_FXML = "views/pokedex-view.fxml";
    private static final String LOGIN_FXML = "views/login-view.fxml";
    private static final String POKEDEX_FXML = "views/pokedex-view.fxml";
    private static final String IMG_ICON_PATH = "img/icon.png";

    @Override
    public void init() {
        SpringContextSingleton.setContext(SpringApplication.run(ProjectSpringBootApp.class));
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle(APP_TITLE);
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream(IMG_ICON_PATH)));
        stage.getIcons().add(icon);
        stage.setResizable(false);

        FXMLLoader fxmlLoader = new FXMLLoader(ProjectJavaFxApp.class.getResource(LOGIN_FXML));
        fxmlLoader.setControllerFactory(SpringContextSingleton.getContext()::getBean);
        Scene scene = new Scene(fxmlLoader.load());

        FXMLLoader pokedexLoader = new FXMLLoader();
        pokedexLoader.setLocation(ProjectJavaFxApp.class.getResource(POKEDEX_FXML));
        pokedexLoader.setControllerFactory(SpringContextSingleton.getContext()::getBean);
        Scene pokedexView = new Scene(pokedexLoader.load());

        MasterController masterController = MasterControllerSingleton.getInstance();
        masterController.setStage(stage);
        masterController.setPokedexView(pokedexView);

        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        SpringContextSingleton.getContext()
                              .close();
    }
}