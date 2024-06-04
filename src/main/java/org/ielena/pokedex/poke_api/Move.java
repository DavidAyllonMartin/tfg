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
    //    @JsonProperty("contest_combos")
//    private ContestComboSets contestCombos;
//    @JsonProperty("contest_effect")
//    private APIResource contestEffect;
//    @JsonProperty("contest_type")
//    private NamedAPIResource contestType;
    @JsonProperty("damage_class")
    private NamedAPIResource damageClass;
    //    @JsonProperty("effect_changes")
//    private List<AbilityEffectChange> effectChanges;
//    @JsonProperty("effect_entries")
//    private List<VerboseEffect> effectEntries;
//    @JsonProperty("learned_by_pokemon")
//    private List<NamedAPIResource> learnedByPokemon;
    @JsonProperty("flavor_text_entries")
    private List<FlavorText> flavorTextEntries;
    @JsonProperty("generation")
    private NamedAPIResource generation;
    //    @JsonProperty("machines")
//    private List<MachineVersionDetail> machines;
//    @JsonProperty("meta")
//    private MoveMetaData meta;
//    @JsonProperty("names")
//    private List<Name> names;
//    @JsonProperty("past_values")
//    private List<PastMoveStatValues> pastValues;
//    @JsonProperty("stat_changes")
//    private List<MoveStatChange> statChanges;
//    @JsonProperty("super_contest_effect")
//    private APIResource superContestEffect;
//    @JsonProperty("target")
//    private NamedAPIResource moveTarget;
    @JsonProperty("type")
    private NamedAPIResource type;
}
