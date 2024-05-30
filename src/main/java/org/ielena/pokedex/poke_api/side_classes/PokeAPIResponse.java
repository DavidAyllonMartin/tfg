package org.ielena.pokedex.poke_api.side_classes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PokeAPIResponse {

    private int count;
    private String next;
    private String previous;
    private List<NamedAPIResource> results;
}

