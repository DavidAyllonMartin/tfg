package org.ielena.pokedex.poke_api.side_classes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MoveMetaData {

    @JsonProperty("ailment")
    private NamedAPIResource ailment;
    @JsonProperty("category")
    private NamedAPIResource category;
    @JsonProperty("min_hits")
    private Integer minHits;
    @JsonProperty("max_hits")
    private Integer maxHits;
    @JsonProperty("min_turns")
    private Integer minTurns;
    @JsonProperty("max_turns")
    private Integer maxTurns;
    @JsonProperty("drain")
    private Integer drain;
    @JsonProperty("healing")
    private Integer healing;
    @JsonProperty("crit_rate")
    private Integer critRate;
    @JsonProperty("ailment_chance")
    private Integer ailmentChance;
    @JsonProperty("flinch_chance")
    private Integer flinchChance;
    @JsonProperty("stat_chance")
    private Integer statChance;
}
