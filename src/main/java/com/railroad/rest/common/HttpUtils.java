package com.railroad.rest.common;

import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Optional;

public class HttpUtils {
    private CloseableHttpResponse connection(HttpUriRequest request) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        return httpClient.execute(request);
    }

    public CloseableHttpResponse get(String url) throws IOException {
        HttpGet request = new HttpGet(url);

        return connection(request);
    }

    public CloseableHttpResponse post(String url, String payload) throws IOException {
        HttpPost request = new HttpPost(url);

        request.setEntity(new StringEntity(payload));
        request.setHeader("Content-Type", "application/json");

        return connection(request);
    }

    public CloseableHttpResponse put(String url, String payload) throws URISyntaxException, IOException {
        URIBuilder uri = new URIBuilder(url);
        return this.put(uri, payload, Optional.empty());
    }

    public CloseableHttpResponse put(URIBuilder builder, String payload, Optional<Map<String, String>> params) throws IOException, URISyntaxException {

        if(params.isPresent())
            params.get().keySet().forEach(key -> builder.setParameter(key, params.get().get(key)));

        URI uri = builder.build();

        HttpPut request = new HttpPut(uri);

        request.setEntity(new StringEntity(payload));
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");

        return connection(request);
    }
}
