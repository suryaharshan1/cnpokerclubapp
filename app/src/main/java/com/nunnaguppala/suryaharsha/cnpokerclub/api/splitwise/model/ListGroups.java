package com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

import java.util.List;

public class ListGroups extends GenericJson {
    @Key("groups")
    private List<Group> groups;

    public List<Group> getGroups() {
        return groups;
    }

    @Override
    public ListGroups clone() {
        return (ListGroups) super.clone();
    }

    @Override
    public ListGroups set(String fieldName, Object value) {
        return (ListGroups) super.set(fieldName, value);
    }
}
