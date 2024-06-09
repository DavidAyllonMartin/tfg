package org.ielena.pokedex.poke_api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.ielena.pokedex.poke_api.side_classes.FlavorText;
import org.ielena.pokedex.poke_api.side_classes.NamedAPIResource;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ability {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("is_main_series")
    private Boolean isMainSeries;

    @JsonProperty("generation")
    private NamedAPIResource generation;

    @JsonProperty("flavor_text_entries")
    private List<FlavorText> flavorTextEntries;
}