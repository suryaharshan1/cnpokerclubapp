package com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

import java.util.List;

public class Expense extends GenericJson {
    @Key("id")
    private int id;

    @Key("group_id")
    private int groupId;

    @Key("friendship_id")
    private Integer friendshipId;

    @Key("expense_bundle_id")
    private Integer expenseBundleId;

    @Key("description")
    private String description;

    @Key("repeats")
    private boolean repeats;

    @Key("repeat_interval")
    private String repeatInterval;

    @Key("email_reminder")
    private boolean emailReminder;

    @Key("email_reminder_in_advance")
    private Integer emailReminderInAdvance;

    @Key("nextRepeat")
    private String nextRepeat;

    @Key("details")
    private String details;

    @Key("comments_count")
    private int commentsCount;

    @Key("payment")
    private boolean payment;

    @Key("creation_method")
    private String creationMethod;

    @Key("transaction_method")
    private String transactionMethod;

    @Key("transaction_confirmed")
    private boolean transactionConfirmed;

    @Key("transaction_id")
    private Integer transactionId;

    @Key("cost")
    private String cost;

    @Key("currency_code")
    private String currencyCode;

    @Key("repayments")
    private List<ExpenseRepayment> repayments;

    @Key("date")
    private String date;

    @Key("created_at")
    private String createdAt;

    @Key("created_by")
    private ExpenseUserDetails createdBy;

    @Key("updated_at")
    private String updatedAt;

    @Key("updated_by")
    private ExpenseUserDetails updatedBy;

    @Key("deleted_at")
    private String deletedAt;

    @Key("deleted_by")
    private ExpenseUserDetails deletedBy;

    @Key("category")
    private ExpenseCategory category;

    @Key("receipt")
    private ExpenseReceipt receipt;

    @Key("users")
    private List<ExpenseUserShare> usersShare;

    public int getId() {
        return id;
    }

    public int getGroupId() {
        return groupId;
    }

    public Integer getFriendshipId() {
        return friendshipId;
    }

    public Integer getExpenseBundleId() {
        return expenseBundleId;
    }

    public String getDescription() {
        return description;
    }

    public boolean isRepeats() {
        return repeats;
    }

    public String getRepeatInterval() {
        return repeatInterval;
    }

    public boolean isEmailReminder() {
        return emailReminder;
    }

    public Integer getEmailReminderInAdvance() {
        return emailReminderInAdvance;
    }

    public String getNextRepeat() {
        return nextRepeat;
    }

    public String getDetails() {
        return details;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public boolean isPayment() {
        return payment;
    }

    public String getCreationMethod() {
        return creationMethod;
    }

    public String getTransactionMethod() {
        return transactionMethod;
    }

    public boolean isTransactionConfirmed() {
        return transactionConfirmed;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public String getCost() {
        return cost;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public List<ExpenseRepayment> getRepayments() {
        return repayments;
    }

    public String getDate() {
        return date;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public ExpenseUserDetails getCreatedBy() {
        return createdBy;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public ExpenseUserDetails getUpdatedBy() {
        return updatedBy;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public ExpenseUserDetails getDeletedBy() {
        return deletedBy;
    }

    public ExpenseCategory getCategory() {
        return category;
    }

    public ExpenseReceipt getReceipt() {
        return receipt;
    }

    public List<ExpenseUserShare> getUsersShare() {
        return usersShare;
    }

    @Override
    public Expense clone() {
        return (Expense) super.clone();
    }

    @Override
    public Expense set(String fieldName, Object value) {
        return (Expense) super.set(fieldName, value);
    }
}
