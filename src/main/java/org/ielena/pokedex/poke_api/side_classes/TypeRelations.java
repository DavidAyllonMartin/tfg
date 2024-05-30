package org.ielena.pokedex.poke_api.side_classes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TypeRelations {

    @JsonProperty("no_damage_to")
    private List<NamedAPIResource> noDamageTo;
    @JsonProperty("half_damage_to")
    private List<NamedAPIResource> halfDamageTo;
    @JsonProperty("double_damage_to")
    private List<NamedAPIResource> doubleDamageTo;
    @JsonProperty("no_damage_from")
    private List<NamedAPIResource> noDamageFrom;
    @JsonProperty("half_damage_from")
    private List<NamedAPIResource> halfDamageFrom;
    @JsonProperty("double_damage_from")
    private List<NamedAPIResource> doubleDamageFrom;
}
