package com.railroad.rest.common;

import com.railroad.entity.AbstractEntity;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.io.IOException;
import java.util.Optional;

public abstract class AbstractService {
    private RestHighLevelClient client;
    private Jsonb jsonb;

    @PostConstruct
    private void init(){
        client = new RestHighLevelClient(RestClient.builder(
                new HttpHost("localhost",9200, "http"),
                new HttpHost("localhost",9300, "http")
        ));
        jsonb = JsonbBuilder.create();
    }

    @PreDestroy
    private void destroy() throws IOException {
        client.close();
    }

    protected void indexDocument(AbstractEntity entity) throws IOException {
        String entityName = entity.getClass().getSimpleName().toLowerCase();
        IndexRequest request = new IndexRequest(entityName);
        request.id(String.format("%s", entity.getId()));
        String payload = jsonb.toJson(entity);
        request.source(payload, XContentType.JSON);
        IndexResponse indexRes = client.index(request, RequestOptions.DEFAULT);
    }

    protected Integer parameterValidation(Integer maxResults, Optional<Integer> firstResults) throws IllegalArgumentException{
        Integer firstRes = firstResults.isPresent() ? firstResults.get() : 0;

        if(firstRes > maxResults)
            throw new IllegalArgumentException("First results cannot be greater to max results");

        return firstRes;
    }
}
