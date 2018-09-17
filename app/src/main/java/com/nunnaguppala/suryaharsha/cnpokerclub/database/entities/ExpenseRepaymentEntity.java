package com.nunnaguppala.suryaharsha.cnpokerclub.database.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

import com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model.Expense;

@Entity(tableName = "expenseRepayment", primaryKeys = {"fromUser", "toUser", "expenseId"})
public class ExpenseRepaymentEntity {
    @ForeignKey(entity = UserEntity.class, parentColumns = {"id"}, childColumns = {"fromUser"})
    private int fromUser;

    @ForeignKey(entity = UserEntity.class, parentColumns = {"id"}, childColumns = {"toUser"})
    private int toUser;

    private String amount;

    @ForeignKey(entity = ExpenseEntity.class, parentColumns = {"id"}, childColumns = {"expenseId"})
    private int expenseId;

    public ExpenseRepaymentEntity(int fromUser, int toUser, String amount, int expenseId) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.amount = amount;
        this.expenseId = expenseId;
    }

    public int getFromUser() {
        return fromUser;
    }

    public int getToUser() {
        return toUser;
    }

    public String getAmount() {
        return amount;
    }

    public int getExpenseId() {
        return expenseId;
    }

    public ExpenseRepaymentEntity setFromUser(int fromUser) {
        this.fromUser = fromUser;
        return this;
    }

    public ExpenseRepaymentEntity setToUser(int toUser) {
        this.toUser = toUser;
        return this;
    }

    public ExpenseRepaymentEntity setAmount(String amount) {
        this.amount = amount;
        return this;
    }

    public ExpenseRepaymentEntity setExpenseId(int expenseId) {
        this.expenseId = expenseId;
        return this;
    }
}
