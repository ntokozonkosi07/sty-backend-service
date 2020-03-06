package com.railroad.common.entityAdapters;

import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;

import com.railroad.entity.AbstractEntity;

public abstract class EntityAdapter<T extends AbstractEntity> implements JsonbAdapter<T, JsonObject> {

}
