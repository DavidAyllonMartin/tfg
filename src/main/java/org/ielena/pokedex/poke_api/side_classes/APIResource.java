package org.ielena.pokedex.poke_api.side_classes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.SneakyThrows;
import org.ielena.pokedex.utils.CachingObjectMapper;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class APIResource {

    @JsonProperty("url")
    private String url;

    @SneakyThrows
    public <T> T createObject(Class<T> tClass) {

        return new CachingObjectMapper().readValue(url, tClass);
    }
}