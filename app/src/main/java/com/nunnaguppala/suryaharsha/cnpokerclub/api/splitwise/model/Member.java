package com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model;

import android.arch.persistence.room.Entity;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

import java.util.List;

public class Member extends GenericJson{

    @Key("id")
    private int id;

    @Key("first_name")
    private String firstName;

    @Key("last_name")
    private String lastName;

    @Key("email")
    private String email;

    @Key("registration_status")
    private String registrationStatus;

    @Key("balance")
    private List<Balance> balances;

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

    public String getEmail() {
        return email;
    }

    public String getRegistrationStatus() {
        return registrationStatus;
    }

    public List<Balance> getBalances() {
        return balances;
    }

    public Picture getPicture() {
        return picture;
    }

    @Override
    public Member clone() {
        return (Member) super.clone();
    }

    @Override
    public Member set(String fieldName, Object value) {
        return (Member) super.set(fieldName, value);
    }
}
