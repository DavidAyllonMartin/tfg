package org.ielena.pokedex.poke_api.side_classes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PokemonAbility {

    @JsonProperty("is_hidden")
    private Boolean isHidden;
    @JsonProperty("slot")
    private Integer slot;
    @JsonProperty("ability")
    private NamedAPIResource ability;
}
