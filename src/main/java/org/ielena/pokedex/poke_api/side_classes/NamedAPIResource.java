package org.ielena.pokedex.poke_api.side_classes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.SneakyThrows;
import org.ielena.pokedex.services.JsonManager;
import org.ielena.pokedex.services.impl.DefaultJsonManager;
import org.ielena.pokedex.singletons.CachingObjectMapper;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NamedAPIResource {

    private JsonManager jsonManager = new DefaultJsonManager();

    @JsonProperty("name")
    private String name;

    @JsonProperty("url")
    private String url;

    @SneakyThrows
    public <T> T createObject(Class<T> typeValue) {

        return new CachingObjectMapper().readValue(url, typeValue);
    }
}