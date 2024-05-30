package org.ielena.pokedex.poke_api.side_classes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PokemonMove {

    @JsonProperty("move")
    private NamedAPIResource move;
    @JsonProperty("version_group_details")
    private List<VersionGroupDetail> versionGroupDetails;
}
