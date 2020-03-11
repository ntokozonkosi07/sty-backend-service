package com.railroad.rest.common;

import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

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
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");

        return connection(request);
    }

    public CloseableHttpResponse put(String url, String payload) throws IOException {
        HttpPut request = new HttpPut(url);

        request.setEntity(new StringEntity(payload));
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");

        return connection(request);
    }
}
