package org.ielena.pokedex.poke_api.side_classes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PokemonSpecies {

    private int id;
    private String name;
    private int order;
    @JsonProperty("gender_rate")
    private int genderRate;
    @JsonProperty("capture_rate")
    private int captureRate;
    @JsonProperty("base_happiness")
    private int baseHappiness;
    @JsonProperty("is_baby")
    private boolean isBaby;
    @JsonProperty("is_legendary")
    private boolean isLegendary;
    @JsonProperty("is_mythical")
    private boolean isMythical;
    @JsonProperty("hatch_counter")
    private int hatchCounter;
    @JsonProperty("has_gender_differences")
    private boolean hasGenderDifferences;
    @JsonProperty("forms_switchable")
    private boolean formsSwitchable;
    private Generation generation;
    private List<Name> names;
    @JsonProperty("flavor_text_entries")
    private List<FlavorText> flavorTextEntries;
}
