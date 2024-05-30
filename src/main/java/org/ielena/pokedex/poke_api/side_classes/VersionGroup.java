package org.ielena.pokedex.poke_api.side_classes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VersionGroup {

    private Long id;

    private String name;

    private Integer order;

    private NamedAPIResource generation;
}
