package com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public class Debt extends GenericJson {
    @Key("from")
    private int from;

    @Key("to")
    private int to;

    @Key("amount")
    private String amount;

    @Key("currency_code")
    private String currencyCode;

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public String getAmount() {
        return amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    @Override
    public Debt clone() {
        return (Debt) super.clone();
    }

    @Override
    public Debt set(String fieldName, Object value) {
        return (Debt) super.set(fieldName, value);
    }
}
