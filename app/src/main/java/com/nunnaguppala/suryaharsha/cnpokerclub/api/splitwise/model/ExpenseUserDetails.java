package com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public class ExpenseUserDetails extends GenericJson {
    @Key("id")
    private int id;

    @Key("first_name")
    private String firstName;

    @Key("last_name")
    private String lastName;

    @Key("picture")
    private Picture picture;

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Picture getPicture() {
        return picture;
    }

    @Override
    public ExpenseUserDetails clone() {
        return (ExpenseUserDetails) super.clone();
    }

    @Override
    public ExpenseUserDetails set(String fieldName, Object value) {
        return (ExpenseUserDetails) super.set(fieldName, value);
    }
}
