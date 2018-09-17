package com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public class ExpenseCategory extends GenericJson {
    @Key("id")
    private int id;

    @Key("name")
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public ExpenseCategory clone() {
        return (ExpenseCategory) super.clone();
    }

    @Override
    public ExpenseCategory set(String fieldName, Object value) {
        return (ExpenseCategory) super.set(fieldName, value);
    }
}
