package com.nunnaguppala.suryaharsha.cnpokerclub.database.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "userBalance")
public class UserBalanceEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    private double amount;
    
    private String currencyCode;
    
    @ForeignKey(entity = UserEntity.class, parentColumns = {"id"}, childColumns = {"userId"})
    private int userId;

    public UserBalanceEntity(int id, double amount, String currencyCode, int userId) {
        this.id = id;
        this.amount = amount;
        this.currencyCode = currencyCode;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public UserBalanceEntity setId(int id) {
        this.id = id;
        return this;
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
