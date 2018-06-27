package com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public class Picture extends GenericJson {
    @Key("small")
    private String small;

    @Key("medium")
    private String medium;

    @Key("large")
    private String large;

    public String getSmall() {
        return small;
    }

    public String getMedium() {
        return medium;
    }

    public String getLarge() {
        return large;
    }

    @Override
    public Picture clone() {
        return (Picture) super.clone();
    }

    @Override
    public Picture set(String fieldName, Object value) {
        return (Picture) super.set(fieldName, value);
    }
}
