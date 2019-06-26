package com.nunnaguppala.suryaharsha.cnpokerclub.database.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "expenseFilter")
public class GameFilterEntity {
    @PrimaryKey
    @ForeignKey(entity = ExpenseEntity.class, parentColumns = {"id"}, childColumns = {"expenseId"})
    private int expenseId;

    private boolean gameFlag;

    private boolean verified;

    public GameFilterEntity(int expenseId, boolean gameFlag, boolean verified) {
        this.expenseId = expenseId;
        this.gameFlag = gameFlag;
        this.verified = verified;
    }

    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }

    public boolean isGameFlag() {
        return gameFlag;
    }

    public void setGameFlag(boolean gameFlag) {
        this.gameFlag = gameFlag;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}
