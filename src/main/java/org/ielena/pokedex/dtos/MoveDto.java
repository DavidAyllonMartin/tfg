package org.ielena.pokedex.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MoveDto {

    private Long id;

    private String name;

    private String accuracy;

    private String effectChance;

    private String pp;

    private String priority;

    private String power;

    private String damageClass;

    private String flavorText;

    private TypeDto type;
}
