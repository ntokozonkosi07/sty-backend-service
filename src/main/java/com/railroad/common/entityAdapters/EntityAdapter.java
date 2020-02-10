package com.railroad.common.entityAdapters;

import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;

import com.railroad.entity.AbstractEntity;

public class EntityAdapter<T extends AbstractEntity> implements JsonbAdapter<T, JsonObject> {

	@Override
	public JsonObject adaptToJson(T obj) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T adaptFromJson(JsonObject obj) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
