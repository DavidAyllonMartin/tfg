package org.ielena.pokedex.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "pokemon")
@Entity
public class PokemonModel {

    @Id
    @Column(name = "pokemon_id")
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(name = "atk", nullable = false)
    private Integer attack;
    @Column(name = "hp", nullable = false)
    private Integer hp;
    @Column(name = "def", nullable = false)
    private Integer defense;
    @Column(name = "spd", nullable = false)
    private Integer speed;
    @Column(name = "sp_atk", nullable = false)
    private Integer specialAttack;
    @Column(name = "sp_def", nullable = false)
    private Integer specialDefense;
    private Integer height;
    private Integer weight;
    @Column(name = "is_default")
    private Boolean isDefault;
    @Column(name = "base_experience")
    private Integer baseExperience;
    @Column(name = "img")
    private String imgUrl;
    @Column(name = "cry")
    private String cryUrl;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "pokemon_abilities",
            joinColumns = {@JoinColumn(name = "pokemon_id", referencedColumnName = "pokemon_id")},
            inverseJoinColumns = {@JoinColumn(name = "ability_id", referencedColumnName = "ability_id")}
    )
    private Set<AbilityModel> abilities;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "pokemon_moves",
            joinColumns = {@JoinColumn(name = "pokemon_id", referencedColumnName = "pokemon_id")},
            inverseJoinColumns = {@JoinColumn(name = "move_id", referencedColumnName = "move_id")}
    )
    private Set<MoveModel> moves;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "pokemon_types",
            joinColumns = {@JoinColumn(name = "pokemon_id", referencedColumnName = "pokemon_id")},
            inverseJoinColumns = {@JoinColumn(name = "type_id", referencedColumnName = "type_id")}
    )
    private Set<TypeModel> types;
}
