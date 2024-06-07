package org.ielena.pokedex.controllers;

import javafx.fxml.FXML;
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

    private static final Map<String, String> DAMAGE_CLASS_COLORS = new HashMap<>();
    private static final String FX_FILL = "-fx-fill: %s;";

    static {
        DAMAGE_CLASS_COLORS.put("Special", "#002bba");
        DAMAGE_CLASS_COLORS.put("Physical", "#9e0000");
        DAMAGE_CLASS_COLORS.put("Status", "#595959");
    }

    @FXML
    public Label name;
    @FXML
    public Label accuracy;
    @FXML
    public Label effectChance;
    @FXML
    public Label pp;
    @FXML
    public Label priority;
    @FXML
    public Label power;
    @FXML
    public Label typeLabel;
    @FXML
    public Label damageClassLabel;
    @FXML
    public Rectangle typeRectangle;
    @FXML
    public Rectangle damageClassRectangle;
    @FXML
    public Button infoButton;

    private boolean tooltipShown = false;

    public void setData(MoveDto moveDto) {
        name.setText(moveDto.getName());
        accuracy.setText(String.valueOf(moveDto.getAccuracy()));
        effectChance.setText(String.valueOf(moveDto.getEffectChance()));
        pp.setText(String.valueOf(moveDto.getPp()));
        power.setText(String.valueOf(moveDto.getPower()));
        damageClassLabel.setText(moveDto.getDamageClass());
        typeLabel.setText(moveDto.getType()
                                 .getName());
        typeRectangle.setStyle(String.format(FX_FILL, moveDto.getType()
                                                             .getColor()));

        String color = DAMAGE_CLASS_COLORS.getOrDefault(moveDto.getDamageClass(), "transparent");
        damageClassRectangle.setStyle(String.format(FX_FILL, color));

        infoButton.setOnAction(event -> {
            if (!tooltipShown) {
                Tooltip tooltip = new Tooltip(moveDto.getFlavorText());
                tooltip.setAutoHide(true);
                tooltip.setWrapText(true);
                tooltip.setPrefWidth(200);

                double tooltipX = infoButton.localToScreen(infoButton.getBoundsInLocal())
                                            .getMinX() - 205;
                double tooltipY = infoButton.localToScreen(infoButton.getBoundsInLocal())
                                            .getMinY() - tooltip.getHeight();

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