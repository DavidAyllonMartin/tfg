package org.ielena.pokedex.poke_api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.ielena.pokedex.poke_api.side_classes.NamedAPIResource;
import org.ielena.pokedex.poke_api.side_classes.PokemonAbility;
import org.ielena.pokedex.poke_api.side_classes.PokemonCries;
import org.ielena.pokedex.poke_api.side_classes.PokemonHeldItem;
import org.ielena.pokedex.poke_api.side_classes.PokemonMove;
import org.ielena.pokedex.poke_api.side_classes.PokemonSprites;
import org.ielena.pokedex.poke_api.side_classes.PokemonStat;
import org.ielena.pokedex.poke_api.side_classes.PokemonType;
import org.ielena.pokedex.poke_api.side_classes.PokemonTypePast;
import org.ielena.pokedex.poke_api.side_classes.VersionGameIndex;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pokemon {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("base_experience")
    private Integer baseExperience;

    @JsonProperty("height")
    private Integer height;

    @JsonProperty("is_default")
    private Boolean isDefault;

    @JsonProperty("order")
    private Integer order;

    @JsonProperty("weight")
    private Integer weight;

    @JsonProperty("abilities")
    private List<PokemonAbility> abilities;

//    @JsonProperty("forms")
//    private List<NamedAPIResource> forms;
//
//    @JsonProperty("game_indices")
//    private List<VersionGameIndex> gameIndices;
//
//    @JsonProperty("held_items")
//    private List<PokemonHeldItem> heldItems;
//
//    @JsonProperty("location_area_encounters")
//    private String locationAreaEncounters;

    @JsonProperty("moves")
    private List<PokemonMove> moves;

//    @JsonProperty("past_types")
//    private List<PokemonTypePast> pastTypes;

    @JsonProperty("sprites")
    private PokemonSprites sprites;

    @JsonProperty("cries")
    private PokemonCries cries;

    @JsonProperty("species")
    private NamedAPIResource species;

    @JsonProperty("stats")
    private List<PokemonStat> stats;

    @JsonProperty("types")
    private List<PokemonType> types;
}