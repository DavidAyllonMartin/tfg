package org.ielena.pokedex.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "pokemon")
@Entity
public class PokemonBasicInfoModel {

    @Id
    @Column(name = "pokemon_id")
    private Long id;
    @Column(nullable = false)
    private String name;
}