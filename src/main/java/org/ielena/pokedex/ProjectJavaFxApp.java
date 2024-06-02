package org.ielena.pokedex;

import jakarta.annotation.Resource;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.ielena.pokedex.controllers.MasterController;
import org.ielena.pokedex.controllers.ViewController;
import org.ielena.pokedex.controllers.mediator.Mediator;
import org.ielena.pokedex.singletons.MasterControllerSingleton;
import org.ielena.pokedex.singletons.SpringContextSingleton;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ProjectJavaFxApp extends Application {

    @Override
    public void init() {
        SpringContextSingleton.setContext(SpringApplication.run(ProjectSpringBootApp.class));
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ProjectJavaFxApp.class.getResource("views/pokedex-view.fxml"));
        fxmlLoader.setControllerFactory(SpringContextSingleton.getContext()::getBean);
        Scene scene = new Scene(fxmlLoader.load());
        MasterController masterController = MasterControllerSingleton.getInstance();
        masterController.setStage(stage);
        masterController.setPokedexView(scene);
        stage.setTitle("Pokedex");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        SpringContextSingleton.getContext()
                              .close();
    }
}