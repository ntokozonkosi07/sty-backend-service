package com.railroad.rest.common;

import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Optional;

public class HttpUtils {

    private CloseableHttpResponse connection(HttpUriRequest request) throws IOException, NoSuchAlgorithmException {
        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(
                SSLContext.getDefault(),
                new String[] { "SSLv2Hello","SSLv3","TLSv1","TLSv1.1","TLSv1.2"},
                null,
                SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", socketFactory)
                .build();

        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);

        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();

//        logger.info(request.toString());

        return httpClient.execute(request);
    }

    public CloseableHttpResponse get(String url) throws IOException, NoSuchAlgorithmException {
        HttpGet request = new HttpGet(url);

        return connection(request);
    }

    public CloseableHttpResponse post(String url, String payload) throws IOException, NoSuchAlgorithmException, URISyntaxException {
        URIBuilder uri = new URIBuilder(url);
        return this.post(uri, payload, Optional.empty());
    }

    public CloseableHttpResponse post(URIBuilder builder, String payload, Optional<Map<String, String>> params) throws URISyntaxException, IOException, NoSuchAlgorithmException {

        if(params.isPresent())
            params.get().keySet().forEach(key -> builder.setParameter(key, params.get().get(key)));

        URI uri = builder.build();

        HttpPost request = new HttpPost(uri);

        request.setEntity(new StringEntity(payload));
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-Type", "application/json");

        return connection(request);
    }

    public CloseableHttpResponse put(String url, String payload) throws URISyntaxException, IOException, NoSuchAlgorithmException {
        URIBuilder uri = new URIBuilder(url);
        return this.put(uri, payload, Optional.empty());
    }

    public CloseableHttpResponse put(URIBuilder builder, String payload, Optional<Map<String, String>> params) throws IOException, URISyntaxException, NoSuchAlgorithmException {

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
