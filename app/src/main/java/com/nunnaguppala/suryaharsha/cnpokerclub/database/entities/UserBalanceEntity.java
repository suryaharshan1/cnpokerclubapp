package com.nunnaguppala.suryaharsha.cnpokerclub.database.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "userBalance", primaryKeys = {"userId", "currencyCode"})
public class UserBalanceEntity {
    private double amount;

    @NonNull
    private String currencyCode;
    
    @ForeignKey(entity = UserEntity.class, parentColumns = {"id"}, childColumns = {"userId"})
    private int userId;

    public UserBalanceEntity(double amount, String currencyCode, int userId) {
        this.amount = amount;
        this.currencyCode = currencyCode;
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public UserBalanceEntity setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public UserBalanceEntity setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public UserBalanceEntity setUserId(int userId) {
        this.userId = userId;
        return this;
    }
}
