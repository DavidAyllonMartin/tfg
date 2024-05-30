package org.ielena.pokedex.poke_api.side_classes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MoveDamageClass {

    private Long id;

    private String name;

    @JsonProperty("descriptions")
    private List<Description> descriptions;

    @JsonProperty("names")
    private List<Name> names;
}

