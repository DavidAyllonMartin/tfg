package org.ielena.pokedex.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import lombok.SneakyThrows;
import org.ielena.pokedex.ProjectJavaFxApp;
import org.ielena.pokedex.dtos.AbilityDto;
import org.ielena.pokedex.dtos.MoveDto;
import org.ielena.pokedex.dtos.TypeDto;
import org.springframework.stereotype.Component;

@Component
public class MoveItemController {
    public Label name;
    public Label accuracy;
    public Label effectChance;
    public Label pp;
    public Label priority;
    public Label power;
    public HBox typeContainer;
    public Label flavorText;

    public void setData(MoveDto moveDto) {
        name.setText(moveDto.getName());
        accuracy.setText(String.valueOf(moveDto.getAccuracy()));
        effectChance.setText(String.valueOf(moveDto.getEffectChance()));
        pp.setText(String.valueOf(moveDto.getPp()));
        priority.setText(String.valueOf(moveDto.getPriority()));
        power.setText(String.valueOf(moveDto.getPower()));
        flavorText.setText(moveDto.getFlavorText());

        addType(moveDto.getType());
        TypeDto damageClass = TypeDto.builder()
                .name(moveDto.getDamageClass())
                .color("#FF00FF")
                                     .build();
        addType(damageClass);

    }

    @SneakyThrows
    private void addType(TypeDto typeDto) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(ProjectJavaFxApp.class.getResource("views/type-container.fxml"));
        AnchorPane anchorPane = fxmlLoader.load();
        TypeContainerController typeContainerController = fxmlLoader.getController();
        typeContainerController.setPokemonType(typeDto, 16);
        typeContainer.getChildren().add(anchorPane);
    }
}
