package org.ielena.pokedex.poke_api.side_classes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VerboseEffect {

    @JsonProperty("effect")
    private String effect;
    @JsonProperty("short_effect")
    private String shortEffect;
    @JsonProperty("language")
    private NamedAPIResource language;
}
