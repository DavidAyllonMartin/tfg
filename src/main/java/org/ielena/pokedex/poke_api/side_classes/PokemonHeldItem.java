package org.ielena.pokedex.poke_api.side_classes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PokemonHeldItem {

    @JsonProperty("item")
    private NamedAPIResource item;
    @JsonProperty("version_details")
    private List<PokemonHeldItemVersion> versionDetails;
}