package org.ielena.pokedex.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ielena.pokedex.singletons.HttpClientSingleton;

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

            if (response.statusCode() == 200) {
                return response.body();
            } else if (url.endsWith("/")) {
                return downloadJson(removeTrailingSlash(url));
            } else {
                System.err.println("Failed to download JSON, status code: " + response.statusCode());
                return "{\"id\":0, \"name\":\"not found\"}";
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "{\"id\":0, \"name\":\"not found\"}";
        }
    }

    private static String removeTrailingSlash(String input) {
        if (input != null && input.endsWith("/")) {
            return input.substring(0, input.length() - 1);
        }
        return input;
    }
}