package org.ielena.pokedex.poke_api.side_classes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Language {

    private Long id;

    private String name;

    private Boolean official;

    private String iso639;

    private String iso3166;
}
