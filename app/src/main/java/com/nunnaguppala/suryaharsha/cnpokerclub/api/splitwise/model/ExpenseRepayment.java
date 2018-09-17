package com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public class ExpenseRepayment extends GenericJson {
    @Key("from")
    private int fromUser;

    @Key("to")
    private int toUser;

    @Key("amount")
    private String amount;

    public int getFromUser() {
        return fromUser;
    }

    public int getToUser() {
        return toUser;
    }

    public String getAmount() {
        return amount;
    }

    @Override
    public ExpenseRepayment clone() {
        return (ExpenseRepayment) super.clone();
    }

    @Override
    public ExpenseRepayment set(String fieldName, Object value) {
        return (ExpenseRepayment) super.set(fieldName, value);
    }
}
