package com.railroad.entity.adapters;

import com.railroad.entity.AbstractEntity;

import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;

public abstract class EntityAdapter<T extends AbstractEntity> implements JsonbAdapter<AbstractEntity, JsonObject> {}