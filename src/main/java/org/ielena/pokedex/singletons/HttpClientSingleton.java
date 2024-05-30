package org.ielena.pokedex.singletons;

import java.net.http.HttpClient;

public class HttpClientSingleton {

    private static HttpClient instance;

    private HttpClientSingleton() {
    }

    public static HttpClient getInstance() {
        if (instance == null) {
            synchronized (HttpClientSingleton.class) {
                if (instance == null) {
                    instance = HttpClient.newHttpClient();
                }
            }
        }
        return instance;
    }
}

