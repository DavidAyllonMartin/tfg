package org.ielena.pokedex.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.ielena.pokedex.dtos.TypeDto;
import org.springframework.stereotype.Component;

@Component
public class TypeItemController {

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