package com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public class ExpenseUserShare extends GenericJson {
    @Key("user")
    private ExpenseUserDetails userDetails;

    @Key("user_id")
    private int userId;

    @Key("paid_share")
    private String paidShare;

    @Key("owed_share")
    private String owedShare;

    @Key("net_balance")
    private String netBalance;

    public ExpenseUserDetails getUserDetails() {
        return userDetails;
    }

    public int getUserId() {
        return userId;
    }

    public String getPaidShare() {
        return paidShare;
    }

    public String getOwedShare() {
        return owedShare;
    }

    public String getNetBalance() {
        return netBalance;
    }

    @Override
    public ExpenseUserShare clone() {
        return (ExpenseUserShare) super.clone();
    }

    @Override
    public ExpenseUserShare set(String fieldName, Object value) {
        return (ExpenseUserShare) super.set(fieldName, value);
    }
}
