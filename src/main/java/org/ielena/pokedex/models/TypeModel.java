package org.ielena.pokedex.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name = "type")
@Entity
public class TypeModel {

    @Id
    @Column(name = "type_id")
    private Long id;

    private String name;

    private String generation;

    @Column(name = "move_damage_class")
    private String moveDamageClass;
}
