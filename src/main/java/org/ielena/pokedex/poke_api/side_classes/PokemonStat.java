package org.ielena.pokedex.poke_api.side_classes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PokemonStat {

    @JsonProperty("base_stat")
    private Integer baseStat;
    @JsonProperty("effort")
    private Integer effort;
    @JsonProperty("stat")
    private NamedAPIResource stat;
}
