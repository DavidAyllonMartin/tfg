package org.ielena.pokedex.singletons;

import java.net.http.HttpClient;

public class HttpClientSingleton {

    private HttpClientSingleton() {
    }

    private static final class InstanceHolder {
        private static final HttpClient instance = HttpClient.newHttpClient();
    }

    public static HttpClient getInstance() {
        return InstanceHolder.instance;
    }
}

