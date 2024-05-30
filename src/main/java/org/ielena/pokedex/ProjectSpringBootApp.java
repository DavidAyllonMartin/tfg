package org.ielena.pokedex;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.ielena.pokedex")
public class ProjectSpringBootApp {

    public static void main(String[] args) {
        Application.launch(ProjectJavaFxApp.class, args);
    }
}
