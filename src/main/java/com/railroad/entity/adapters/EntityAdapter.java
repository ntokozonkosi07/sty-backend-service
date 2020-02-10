package com.railroad.entity.adapters;

import com.railroad.entity.AbstractEntity;
import com.railroad.entity.User;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;
import java.util.List;

public abstract class EntityAdapter<T extends AbstractEntity> implements JsonbAdapter<AbstractEntity, JsonObject> {}