package com.nunnaguppala.suryaharsha.cnpokerclub.database.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "simplifiedDebt", primaryKeys = {"fromUser", "toUser", "groupId", "currencyCode"})
public class SimplifiedDebtEntity {
    @ForeignKey(entity = UserEntity.class, parentColumns = {"id"}, childColumns = {"fromUser"})
    private int fromUser;

    @ForeignKey(entity = UserEntity.class, parentColumns = {"id"}, childColumns = {"toUser"})
    private int toUser;

    private String amount;

    @NonNull
    private String currencyCode;

    @ForeignKey(entity = GroupEntity.class, parentColumns = {"id"}, childColumns = {"groupId"})
    private int groupId;

    public SimplifiedDebtEntity(int fromUser, int toUser, String amount, String currencyCode, int groupId) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.amount = amount;
        this.currencyCode = currencyCode;
        this.groupId = groupId;
    }

    public int getFromUser() {
        return fromUser;
    }

    public SimplifiedDebtEntity setFromUser(int fromUser) {
        this.fromUser = fromUser;
        return this;
    }

    public int getToUser() {
        return toUser;
    }

    public SimplifiedDebtEntity setToUser(int toUser) {
        this.toUser = toUser;
        return this;
    }

    public String getAmount() {
        return amount;
    }

    public SimplifiedDebtEntity setAmount(String amount) {
        this.amount = amount;
        return this;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public SimplifiedDebtEntity setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    public int getGroupId() {
        return groupId;
    }

    public SimplifiedDebtEntity setGroupId(int groupId) {
        this.groupId = groupId;
        return this;
    }
}
