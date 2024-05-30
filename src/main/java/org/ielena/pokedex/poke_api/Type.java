package org.ielena.pokedex.poke_api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.ielena.pokedex.poke_api.side_classes.GameIndex;
import org.ielena.pokedex.poke_api.side_classes.Name;
import org.ielena.pokedex.poke_api.side_classes.NamedAPIResource;
import org.ielena.pokedex.poke_api.side_classes.TypeRelations;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Type {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
//    @JsonProperty("damage_relations")
//    private TypeRelations damageRelations;
//    @JsonProperty("game_indices")
//    private List<GameIndex> gameIndices;
    @JsonProperty("generation")
    private NamedAPIResource generation;
    @JsonProperty("move_damage_class")
    private NamedAPIResource moveDamageClass;
//    @JsonProperty("names")
//    private List<Name> names;
}
