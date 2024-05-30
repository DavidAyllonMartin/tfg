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
@Table(name = "ability")
@Entity
public class AbilityModel {

    @Id
    @Column(name = "ability_id")
    private Long id;

    private String name;

    @Column(name = "is_main_series")
    private Boolean isMainSeries;

    private String generation;

    @Column(name = "flavor_text")
    private String flavorText;
}