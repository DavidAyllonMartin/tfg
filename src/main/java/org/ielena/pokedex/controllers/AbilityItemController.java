package org.ielena.pokedex.controllers;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.ielena.pokedex.dtos.AbilityDto;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class AbilityItemController {

    public Label name;

    public Label description;
    public VBox coloredPart;
    public VBox whitePart;

    public void setData(AbilityDto abilityDto, String color) {
        name.setText(abilityDto.getName());
        description.setText(abilityDto.getEffect());
        coloredPart.setStyle(String.format("-fx-background-color: %s;", color));

        Color headerColor = Color.decode(color);
        Color lighterColor = makeLighter(headerColor);

        String lighterColorHex = String.format("#%02x%02x%02x",
                lighterColor.getRed(), lighterColor.getGreen(), lighterColor.getBlue());
        whitePart.setStyle(String.format("-fx-background-color: %s;", lighterColorHex));
    }

    private Color makeLighter(Color color) {
        int r = color.getRed() + (255 - color.getRed()) / 2;
        int g = color.getGreen() + (255 - color.getGreen()) / 2;
        int b = color.getBlue() + (255 - color.getBlue()) / 2;
        return new Color(r, g, b);
    }
}
