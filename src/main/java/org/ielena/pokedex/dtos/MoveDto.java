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

    private Integer accuracy;

    private Integer effectChance;

    private Integer pp;

    private Integer priority;

    private Integer power;

    private String damageClass;

    private String flavorText;

    private TypeDto type;
}
