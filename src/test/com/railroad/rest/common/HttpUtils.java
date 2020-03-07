package com.railroad.rest.common;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;

public class HttpUtils {
    private CloseableHttpResponse connection(HttpUriRequest request) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = httpClient.execute(request);

        return response;
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
