package org.ielena.pokedex;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.ielena.pokedex.controllers.MasterController;
import org.ielena.pokedex.singletons.MasterControllerSingleton;
import org.ielena.pokedex.singletons.SpringContextSingleton;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ProjectJavaFxApp extends Application {

    private static final String APP_TITLE = "Pokedex";
    public static final String VIEW_FXML = "views/pokedex-view.fxml";

    @Override
    public void init() {
        SpringContextSingleton.setContext(SpringApplication.run(ProjectSpringBootApp.class));
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ProjectJavaFxApp.class.getResource(VIEW_FXML));
        fxmlLoader.setControllerFactory(SpringContextSingleton.getContext()::getBean);
        Scene scene = new Scene(fxmlLoader.load());
        MasterController masterController = MasterControllerSingleton.getInstance();
        masterController.setStage(stage);
        masterController.setPokedexView(scene);
        stage.setTitle(APP_TITLE);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        SpringContextSingleton.getContext()
                              .close();
    }
}