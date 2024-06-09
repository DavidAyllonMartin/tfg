package org.ielena.pokedex.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.ielena.pokedex.dtos.AbilityDto;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class AbilityItemController {

    private static final String FX_BACKGROUND_COLOR = "-fx-background-color: %s;";

    @FXML
    public Label name;
    @FXML
    public Label description;
    @FXML
    public VBox coloredPart;
    @FXML
    public VBox whitePart;

    public void setData(AbilityDto abilityDto, String color) {
        name.setText(abilityDto.getName());
        description.setText(abilityDto.getEffect());
        coloredPart.setStyle(String.format(FX_BACKGROUND_COLOR, color));

        Color headerColor = Color.decode(color);
        Color lighterColor = makeLighter(headerColor);

        String lighterColorHex = String.format("#%02x%02x%02x",
                lighterColor.getRed(), lighterColor.getGreen(), lighterColor.getBlue());
        whitePart.setStyle(String.format(FX_BACKGROUND_COLOR, lighterColorHex));
    }

    private Color makeLighter(Color color) {
        int r = color.getRed() + (255 - color.getRed()) / 2;
        int g = color.getGreen() + (255 - color.getGreen()) / 2;
        int b = color.getBlue() + (255 - color.getBlue()) / 2;
        return new Color(r, g, b);
    }
}
