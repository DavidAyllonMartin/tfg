package org.ielena.pokedex.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import org.ielena.pokedex.dtos.TypeDto;
import org.springframework.stereotype.Component;

@Component
public class TypeContainerController {

    @FXML
    private HBox typeContainer;

    @FXML
    private Label typeName;

    public void setPokemonType(TypeDto typeDto, Integer fontSize) {
        typeName.setText(typeDto.getName());
        typeName.setStyle(String.format("-fx-font-size: %dpx;", fontSize));
        typeContainer.setStyle(String.format("-fx-background-color: %s;", typeDto.getColor()));
    }
}

