package org.ielena.pokedex.dtos;

import javafx.scene.image.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PokemonDto {

    List<MoveDto> moves;
    List<AbilityDto> abilities;
    List<TypeDto> types;
    private Long id;
    private String name;
    private Integer attack;
    private Integer hp;
    private Integer defense;
    private Integer speed;
    private Integer specialAttack;
    private Integer specialDefense;
    private Integer height;
    private Integer weight;
    private Boolean isDefault;
    private Integer baseExperience;
    private String imgUrl;
    private String cryUrl;
    private String color;
    private Image img;
    private byte[] cry;
}
