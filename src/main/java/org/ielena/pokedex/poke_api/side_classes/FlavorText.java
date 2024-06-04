package org.ielena.pokedex.poke_api.side_classes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlavorText {

    @JsonProperty("flavor_text")
    private String flavorText;
    @JsonProperty("language")
    private NamedAPIResource language;

    @JsonProperty("version_group")
    private NamedAPIResource versionGroup;

    public String getFlavorText() {
        return flavorText.replace("\n", " ")
                         .replace("\f", " ")
                         .replace("POKéMON", "pokémon")
                         .replace("- ", "");
    }
}
