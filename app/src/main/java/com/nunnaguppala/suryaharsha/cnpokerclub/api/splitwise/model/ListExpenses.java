package com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

import java.util.List;

public class ListExpenses extends GenericJson {
    @Key("expenses")
    private List<Expense> expenses;

    public List<Expense> getExpenses() {
        return expenses;
    }

    @Override
    public ListExpenses clone() {
        return (ListExpenses) super.clone();
    }

    @Override
    public ListExpenses set(String fieldName, Object value) {
        return (ListExpenses) super.set(fieldName, value);
    }
}
