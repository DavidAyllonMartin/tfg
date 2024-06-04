package org.ielena.pokedex.controllers;

import javafx.scene.control.Label;
import org.ielena.pokedex.dtos.MoveDto;
import org.springframework.stereotype.Component;

@Component
public class MoveItemController {
    public Label name;
    public Label accuracy;
    public Label effectChance;
    public Label pp;
    public Label priority;
    public Label power;
    public Label flavorText;
    public Label typeLabel;
    public Label damageClassLabel;

    public void setData(MoveDto moveDto) {
        name.setText(moveDto.getName());
        accuracy.setText(String.valueOf(moveDto.getAccuracy()));
        effectChance.setText(String.valueOf(moveDto.getEffectChance()));
        pp.setText(String.valueOf(moveDto.getPp()));
        priority.setText(String.valueOf(moveDto.getPriority()));
        power.setText(String.valueOf(moveDto.getPower()));
        flavorText.setText(moveDto.getFlavorText());
        damageClassLabel.setText(moveDto.getDamageClass());
        typeLabel.setText(moveDto.getType()
                                 .getName());

    }
}
