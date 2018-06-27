package com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

import java.util.List;

public abstract class SplitwiseResponse extends GenericJson{
    public static final String KEY_DATA = "data";

    @Key("errors")
    private List<Error> errors;

    public final List<Error> getErrors() {
        return errors;
    }

    @Override
    public SplitwiseResponse clone() {
        return (SplitwiseResponse) super.clone();
    }

    @Override
    public SplitwiseResponse set(String fieldName, Object value) {
        return (SplitwiseResponse) super.set(fieldName, value);
    }
}
