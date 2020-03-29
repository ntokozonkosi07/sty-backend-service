package com.railroad.rest.role;

import com.railroad.common.entityAdapters.EntityAdapter;
import com.railroad.entity.Role;

import javax.json.*;
import java.util.ArrayList;

public class RoleMashaler extends EntityAdapter<Role> {
    @Override
    public JsonObject adaptToJson(Role obj) throws Exception {
        JsonArray jsonArrBuilder = Json.createArrayBuilder().build();

        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder()
                .add("name", obj.getName())
                .add("userRoles", jsonArrBuilder);

        if(obj.getId() != null){
            jsonBuilder.add("id", obj.getId());
        }
        return jsonBuilder.build();
    }

    @Override
    public Role adaptFromJson(JsonObject obj) throws Exception {
        Role role = new Role();
        role.setId(((JsonNumber)obj.get("id")).longValue());
        role.setName(((JsonString)obj.get("name")).getString());
        role.setUserRoles(new ArrayList<>());
        return role;
    }
}
