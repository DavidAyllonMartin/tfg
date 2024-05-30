package org.ielena.pokedex.poke_api.side_classes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContestComboDetail {

    @JsonProperty("use_before")
    private List<NamedAPIResource> useBefore;
    @JsonProperty("use_after")
    private List<NamedAPIResource> useAfter;
}
