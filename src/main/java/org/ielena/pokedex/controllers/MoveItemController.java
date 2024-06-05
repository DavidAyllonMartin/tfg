package org.ielena.pokedex.controllers;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.shape.Rectangle;
import org.ielena.pokedex.dtos.MoveDto;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MoveItemController {

    public Label name;
    public Label accuracy;
    public Label effectChance;
    public Label pp;
    public Label priority;
    public Label power;
    public Label typeLabel;
    public Label damageClassLabel;
    public Rectangle typeRectangle;
    public Rectangle damageClassRectangle;
    public Button infoButton;
    private boolean tooltipShown = false;

    // Static map to define colors for each damage class
    private static final Map<String, String> DAMAGE_CLASS_COLORS = new HashMap<>();

    // Static block to initialize the map
    static {
        DAMAGE_CLASS_COLORS.put("Special", "#002bba");
        DAMAGE_CLASS_COLORS.put("Physical", "#9e0000");
        DAMAGE_CLASS_COLORS.put("Status", "#595959");
    }

    public void setData(MoveDto moveDto) {
        name.setText(moveDto.getName());
        accuracy.setText(String.valueOf(moveDto.getAccuracy()));
        effectChance.setText(String.valueOf(moveDto.getEffectChance()));
        pp.setText(String.valueOf(moveDto.getPp()));
        priority.setText(String.valueOf(moveDto.getPriority()));
        power.setText(String.valueOf(moveDto.getPower()));
        damageClassLabel.setText(moveDto.getDamageClass());
        typeLabel.setText(moveDto.getType().getName());
        typeRectangle.setStyle(String.format("-fx-fill: %s;", moveDto.getType().getColor()));

        // Set the background color based on the damage class using the static map
        String color = DAMAGE_CLASS_COLORS.getOrDefault(moveDto.getDamageClass(), "transparent");
        damageClassRectangle.setStyle(String.format("-fx-fill: %s;", color));

        infoButton.setOnAction(event -> {
            if (!tooltipShown) {
                Tooltip tooltip = new Tooltip(moveDto.getFlavorText());
                tooltip.setAutoHide(true);
                tooltip.setWrapText(true);
                tooltip.setPrefWidth(200);

                double tooltipX = infoButton.localToScreen(infoButton.getBoundsInLocal()).getMinX() - 205;
                double tooltipY = infoButton.localToScreen(infoButton.getBoundsInLocal()).getMinY() - tooltip.getHeight();

                tooltip.show(infoButton, tooltipX, tooltipY);
                tooltipShown = true;

                infoButton.setOnMouseExited(e -> {
                    tooltip.hide();
                    tooltipShown = false;
                });
            }
        });
    }
}