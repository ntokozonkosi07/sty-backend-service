package com.railroad.entity.adapters;

import com.railroad.entity.AbstractEntity;

import javax.json.JsonArray;
import javax.json.bind.adapter.JsonbAdapter;
import java.util.Collection;

public abstract class GenericCollectionAdapter<T> implements JsonbAdapter<Collection<T>, JsonArray> {}
