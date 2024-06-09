package org.ielena.pokedex.poke_api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.ielena.pokedex.poke_api.side_classes.FlavorText;
import org.ielena.pokedex.poke_api.side_classes.NamedAPIResource;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Move {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("accuracy")
    private Integer accuracy;
    @JsonProperty("effect_chance")
    private Integer effectChance;
    @JsonProperty("pp")
    private Integer pp;
    @JsonProperty("priority")
    private Integer priority;
    @JsonProperty("power")
    private Integer power;
    @JsonProperty("damage_class")
    private NamedAPIResource damageClass;
    @JsonProperty("flavor_text_entries")
    private List<FlavorText> flavorTextEntries;
    @JsonProperty("generation")
    private NamedAPIResource generation;
    @JsonProperty("type")
    private NamedAPIResource type;
}