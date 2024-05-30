package org.ielena.pokedex.poke_api.side_classes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PokemonType {

    @JsonProperty("slot")
    private Integer slot;
    @JsonProperty("type")
    private NamedAPIResource type;
}
