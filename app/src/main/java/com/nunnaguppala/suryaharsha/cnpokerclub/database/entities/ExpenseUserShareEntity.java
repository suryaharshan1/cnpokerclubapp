package com.nunnaguppala.suryaharsha.cnpokerclub.database.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

@Entity(tableName = "expenseUserShare", primaryKeys = {"userId", "expenseId"})
public class ExpenseUserShareEntity {
    @ForeignKey(entity = UserEntity.class, parentColumns = {"id"}, childColumns = {"userId"})
    private int userId;

    @ForeignKey(entity = ExpenseEntity.class, parentColumns = {"id"}, childColumns = {"expenseId"})
    private int expenseId;

    private String paidShare;

    private String owedShare;

    private String netBalance;

    public ExpenseUserShareEntity(int userId, int expenseId, String paidShare, String owedShare, String netBalance) {
        this.userId = userId;
        this.expenseId = expenseId;
        this.paidShare = paidShare;
        this.owedShare = owedShare;
        this.netBalance = netBalance;
    }

    public int getUserId() {
        return userId;
    }

    public ExpenseUserShareEntity setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public int getExpenseId() {
        return expenseId;
    }

    public ExpenseUserShareEntity setExpenseId(int expenseId) {
        this.expenseId = expenseId;
        return this;
    }

    public String getPaidShare() {
        return paidShare;
    }

    public ExpenseUserShareEntity setPaidShare(String paidShare) {
        this.paidShare = paidShare;
        return this;
    }

    public String getOwedShare() {
        return owedShare;
    }

    public ExpenseUserShareEntity setOwedShare(String owedShare) {
        this.owedShare = owedShare;
        return this;
    }

    public String getNetBalance() {
        return netBalance;
    }

    public ExpenseUserShareEntity setNetBalance(String netBalance) {
        this.netBalance = netBalance;
        return this;
    }
}
