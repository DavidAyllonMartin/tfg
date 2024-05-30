package org.ielena.pokedex.poke_api.side_classes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PastMoveStatValues {

    @JsonProperty("accuracy")
    private Integer accuracy;
    @JsonProperty("effect_chance")
    private Integer effectChance;
    @JsonProperty("power")
    private Integer power;
    @JsonProperty("pp")
    private Integer pp;
    @JsonProperty("effect_entries")
    private List<VerboseEffect> effectEntries;
    @JsonProperty("type")
    private NamedAPIResource type;
    @JsonProperty("version_group")
    private NamedAPIResource versionGroup;
}
