package com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public class ExpenseReceipt extends GenericJson {
    @Key("large")
    private String largeReceipt;

    @Key("original")
    private String originalReceipt;

    public String getLargeReceipt() {
        return largeReceipt;
    }

    public String getOriginalReceipt() {
        return originalReceipt;
    }

    @Override
    public ExpenseReceipt clone() {
        return (ExpenseReceipt) super.clone();
    }

    @Override
    public ExpenseReceipt set(String fieldName, Object value) {
        return (ExpenseReceipt) super.set(fieldName, value);
    }
}
