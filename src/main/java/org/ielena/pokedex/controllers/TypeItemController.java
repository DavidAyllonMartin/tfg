package org.ielena.pokedex.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.ielena.pokedex.dtos.TypeDto;
import org.springframework.stereotype.Component;

@Component
public class TypeItemController {

    private static final String FX_BACKGROUND_COLOR = "-fx-background-color: %s;";
    private static final String FX_FONT_SIZE = "-fx-font-size: %d;";

    @FXML
    private HBox typeContainer;
    @FXML
    private Label typeName;

    public void setPokemonType(TypeDto typeDto, Integer fontSize) {
        typeName.setText(typeDto.getName());
        typeName.setStyle(String.format(FX_FONT_SIZE, fontSize));
        typeContainer.setStyle(String.format(FX_BACKGROUND_COLOR, typeDto.getColor()));
    }
}