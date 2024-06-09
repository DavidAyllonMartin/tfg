package org.ielena.pokedex.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class LoadingWindowController implements DatabaseUpdateListener {

    @FXML
    public Label loadingLabel;
    @FXML
    public ProgressBar progressBar;

    @Override
    public void onPokemonGenerated(int processed, int total) {
        Platform.runLater(() -> {
            if (progressBar != null) {
                progressBar.setProgress((double) processed / total);
            }
        });
    }
}