package org.ielena.pokedex.poke_api.side_classes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContestComboSets {

    @JsonProperty("normal")
    private ContestComboDetail normal;
    @JsonProperty("super")
    private ContestComboDetail sup;
}
