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

import static org.ielena.pokedex.utils.Constants.Img.IMG_ICON_PATH;
import static org.ielena.pokedex.utils.Constants.View.LOGIN_FXML_PATH;

@Component
public class ProjectJavaFxApp extends Application {

    private static final String APP_TITLE = "Pokedex";

    @Override
    public void init() {
        SpringContextSingleton.setContext(SpringApplication.run(ProjectSpringBootApp.class));
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle(APP_TITLE);
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream(IMG_ICON_PATH)));
        stage.getIcons()
             .add(icon);
        stage.setResizable(false);

        FXMLLoader fxmlLoader = new FXMLLoader(ProjectJavaFxApp.class.getResource(LOGIN_FXML_PATH));
        fxmlLoader.setControllerFactory(SpringContextSingleton.getContext()::getBean);
        Scene scene = new Scene(fxmlLoader.load());

        MasterController masterController = MasterControllerSingleton.getInstance();
        masterController.setStage(stage);

        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        SpringContextSingleton.getContext()
                              .close();
    }
}