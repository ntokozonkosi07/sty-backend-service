package com.railroad.rest.common.elasticsearch;

import com.railroad.common.PropertyValues;
import com.railroad.entity.AbstractEntity;
import com.railroad.rest.common.HttpUtils;
import com.railroad.rest.user.UserAdapter;
import lombok.Cleanup;
import lombok.var;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.utils.URIBuilder;

import javax.annotation.PostConstruct;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
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

    private void createIndex(String name) throws Exception {
        builder.setPath(name.toLowerCase());
        @Cleanup CloseableHttpResponse response = http.put(builder,"", Optional.empty());

        if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
            throw new Exception("Could not create index");
    }

    private void createIndexMapping(T entity) throws Exception {
        StringBuffer buffer = new StringBuffer();

        String type = entity.getClass().getSimpleName().toLowerCase();

        builder.setPath(String.format("%s/_mapping/%s_type", type,type));

        for (Field field: entity.getClass().getSuperclass().getDeclaredFields())
            buffer.append(String.format("\"%s\":{ \"type\": \"%s\" },", field.getName(), getElasticType(field.getType().toString())));

        var fields = entity.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            if(!Modifier.isStatic(fields[i].getModifiers())){
                buffer.append(String.format(
                        "\"%s\":{ \"type\": \"%s\" }%s",
                        fields[i].getName(),
                        getElasticType(fields[i].getType().toString()),i == fields.length -1 ? "": ","));
            }
        }

        String mapping = String.format("{\"%s_type\":{\"properties\":{%s}}}",entity.getClass().getSimpleName().toLowerCase(), buffer.toString());

        @Cleanup CloseableHttpResponse response = http.put(builder,mapping, Optional.empty());
        if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
            throw new Exception("Could not create index");
    }

    private String getElasticType(String type){
        switch (type){
            case "class java.lang.Long":
                return "integer";
            case "class [B":
            case "class java.lang.String":
                return "text";
            case "interface java.util.Collection":
                return "nested";
            default:
                return type;
        }
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
            createIndexMapping(entity);
        }

        var type = entity.getClass().getSimpleName().toLowerCase();
        String payload = jsonb.toJson(entity);
        @Cleanup CloseableHttpResponse response = http.post(String.format("%s/%s_type/%s",builder.toString(), type, entity.getId()), payload);

         int httpStateCode = response.getStatusLine().getStatusCode();
        if(httpStateCode != 201){
            throw new Exception(response.toString());
        }
    }

    public void updateDoc(T entity) throws Exception {
        boolean indexExist = indexExist(entity.getClass().getSimpleName());
        String payload = String.format("{\"doc\":%s}",jsonb.toJson(entity));

        var type = entity.getClass().getSimpleName().toLowerCase();

        @Cleanup CloseableHttpResponse response = http.post(String.format("%s/%s_type/%s/%s",builder.toString(), type, entity.getId(),"_update"), payload);

        int httpStateCode = response.getStatusLine().getStatusCode();
        if(httpStateCode != 200){
            throw new Exception(response.toString());
        }
    }
}
