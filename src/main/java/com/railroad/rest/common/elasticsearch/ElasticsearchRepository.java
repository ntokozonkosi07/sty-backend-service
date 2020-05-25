package com.railroad.rest.common.elasticsearch;

import com.railroad.common.PropertyValues;
import com.railroad.entity.AbstractEntity;
import com.railroad.rest.common.HttpUtils;
import com.railroad.rest.user.UserAdapter;
import lombok.Cleanup;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.utils.URIBuilder;

import javax.annotation.PostConstruct;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

public abstract class ElasticsearchRepository<T extends AbstractEntity> {
    private HttpUtils http;
    private PropertyValues properties;
    private URIBuilder builder;
    private Jsonb jsonb;

    @PostConstruct
    private void init() throws IOException {
        this.http = new HttpUtils();
        this.properties = new PropertyValues();
        builder = new URIBuilder();
        builder.setScheme(this.properties.getValue("scheme"))
                .setHost(this.properties.getValue("host"))
                .setPort(Integer.parseInt(this.properties.getValue("port")));
        JsonbConfig config = new JsonbConfig().withAdapters(new UserAdapter());
        this.jsonb = JsonbBuilder.create(config);
    }

    private boolean createIndex(String name) throws IOException, URISyntaxException {
        builder.setPath(name.toLowerCase());
        @Cleanup CloseableHttpResponse response = http.put(builder,"", Optional.empty());

        return response.getStatusLine().getStatusCode() == 200;
    }

    private boolean indexExist(String name) throws IOException {
        builder.setPath(name.toLowerCase());
        @Cleanup CloseableHttpResponse response = http.get(builder.toString());
        return response.getStatusLine().getStatusCode() == 200;
    }

    public void insertDoc(T entity) throws Exception {
        boolean indexExist = indexExist(entity.getClass().getSimpleName());
        if(!indexExist){
           createIndex(entity.getClass().getSimpleName());
        }

        String payload = jsonb.toJson(entity);
        @Cleanup CloseableHttpResponse response = http.post(String.format("%s/%s",builder.toString(), entity.getId()), payload);

         int httpStateCode = response.getStatusLine().getStatusCode();
        if(httpStateCode != 201){
            throw new Exception(response.toString());
        }
    }

    public void updateDoc(T entity) throws Exception {
        boolean indexExist = indexExist(entity.getClass().getSimpleName());
        String payload = String.format("{\"doc\":%s}",jsonb.toJson(entity));
        @Cleanup CloseableHttpResponse response = http.post(String.format("%s/%s/%s",builder.toString(), entity.getId(),"_update"), payload);

        int httpStateCode = response.getStatusLine().getStatusCode();
        if(httpStateCode != 200){
            throw new Exception(response.toString());
        }
    }
}
