package org.ielena.pokedex.poke_api.side_classes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VersionGroupDetail {

    @JsonProperty("level_learned_at")
    private Integer levelLearnedAt;
    @JsonProperty("version_group")
    private NamedAPIResource versionGroup;
    @JsonProperty("move_learn_method")
    private NamedAPIResource moveLearnMethod;
}
