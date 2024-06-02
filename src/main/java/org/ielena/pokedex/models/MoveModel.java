package org.ielena.pokedex.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "move")
@Entity
public class MoveModel {

    @Id
    @Column(name = "move_id")
    private Long id;
    private String name;
    private Integer accuracy;
    @Column(name = "effect_chance")
    private Integer effectChance;
    private Integer pp;
    private Integer priority;
    private Integer power;
    @Column(name = "move_damage_class")
    private String moveDamageClass;
    @Column(name = "flavor_text")
    private String flavorText;
    private String generation;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "type_id")
    private TypeModel type;
}