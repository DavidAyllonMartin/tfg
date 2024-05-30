package org.ielena.pokedex.services;

import java.util.concurrent.CompletableFuture;

public interface JsonManager {

    CompletableFuture<String> fetchJsonFromUrlAsync(String url);

    String fetchJsonFromUrlSync(String url);
}
