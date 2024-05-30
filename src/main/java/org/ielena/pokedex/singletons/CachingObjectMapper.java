package org.ielena.pokedex.singletons;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CachingObjectMapper extends ObjectMapper {

    @Override
    public <T> T readValue(String url, Class<T> valueType) throws JsonProcessingException {

        if (ObjectCache.isInCache(url)) {

            return (T) ObjectCache.getFromCache(url);
        } else {

            String content = downloadJson(url);
            T deserializedObject = super.readValue(content, valueType);

            ObjectCache.addToCache(url, deserializedObject);

            return deserializedObject;
        }
    }

    private String downloadJson(String url) {
        HttpRequest request = HttpRequest.newBuilder()
                                         .uri(URI.create(url))
                                         .GET()
                                         .build();

        try {
            HttpResponse<String> response = HttpClientSingleton.getInstance()
                                                               .send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}

