package org.ielena.pokedex.controllers;

import javafx.scene.control.Label;
import org.ielena.pokedex.dtos.AbilityDto;

public class AbilityItemController {
    public Label name;
    public Label description;

    public void setData(AbilityDto abilityDto){
        name.setText(abilityDto.getName());
        description.setText(abilityDto.getEffect());
    }
}
