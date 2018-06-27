package com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public class Balance extends GenericJson {

    @Key("currency_code")
    private String currencyCode;

    @Key("amount")
    private double amount;

    public String getCurrencyCode() {
        return currencyCode;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public Balance clone() {
        return (Balance) super.clone();
    }

    @Override
    public Balance set(String fieldName, Object value) {
        return (Balance) super.set(fieldName, value);
    }
}
