package com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

import java.util.List;

public class CreateExpense extends GenericJson {
    @Key("group_id")
    private int groupId;

    @Key("description")
    private String description;

    @Key("currency_code")
    private String currencyCode;

    @Key("cost")
    private String cost;

    @Key("users")
    private List<ExpenseUserShare> usersShare;

    @Key("category_id")
    private int category_id;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public List<ExpenseUserShare> getUsersShare() {
        return usersShare;
    }

    public void setUsersShare(List<ExpenseUserShare> usersShare) {
        this.usersShare = usersShare;
    }

    public int getCategoryID() {
        return category_id;
    }

    public void setCategoryID(int category) {
        this.category_id = category;
    }
}
