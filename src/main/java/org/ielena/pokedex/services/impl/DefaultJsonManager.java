package org.ielena.pokedex.services.impl;

import org.springframework.stereotype.Service;
import org.ielena.pokedex.services.JsonManager;
import org.ielena.pokedex.singletons.HttpClientSingleton;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

@Service
public class DefaultJsonManager implements JsonManager {

    @Override
    public CompletableFuture<String> fetchJsonFromUrlAsync(String url) {
        HttpRequest request = HttpRequest.newBuilder()
                                         .uri(URI.create(url))
                                         .GET()
                                         .build();

        return HttpClientSingleton.getInstance()
                                  .sendAsync(request, HttpResponse.BodyHandlers.ofString())
                                  .thenApply(HttpResponse::body);
    }

    @Override
    public String fetchJsonFromUrlSync(String url) {
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
