package org.ielena.pokedex.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ielena.pokedex.singletons.HttpClientSingleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CachingObjectMapper extends ObjectMapper {

    private static final Logger logger = LoggerFactory.getLogger(CachingObjectMapper.class);

    @Override
    public <T> T readValue(String url, Class<T> valueType) throws JsonProcessingException {

        if (ObjectCache.isInCache(url)) {
//            logger.info("Cache hit for URL: {}", url);

            return (T) ObjectCache.getFromCache(url);
        } else {
//            logger.info("Cache miss for URL: {}. Downloading JSON.", url);
            String content = downloadJson(url);
            if (content == null) {
                logger.error("Failed to download JSON from URL: {}", url);
//                ObjectCache.addToCache(url, null);

                return null;
            }
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
//                logger.info("Successfully downloaded JSON from URL: {}", url);

                return response.body();
            } else if (url.endsWith("/")) {
                logger.warn("Received status code {} for URL: {}. Retrying without trailing slash.", response.statusCode(), url);

                return downloadJson(removeTrailingSlash(url));
            } else {
                logger.error("Failed to download JSON from URL: {}. Status code: {}", url, response.statusCode());

                return null;
            }

        } catch (IOException | InterruptedException e) {
            logger.error("Exception occurred while downloading JSON from URL: {}", url, e);

            return null;
        }
    }

    private static String removeTrailingSlash(String input) {
        if (input != null && input.endsWith("/")) {
            return input.substring(0, input.length() - 1);
        }
        return input;
    }
}