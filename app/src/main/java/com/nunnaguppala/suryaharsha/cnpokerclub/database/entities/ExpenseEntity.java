package com.nunnaguppala.suryaharsha.cnpokerclub.database.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "expense")
public class ExpenseEntity {
    @PrimaryKey
    private int id;

    @ForeignKey(entity = GroupEntity.class, parentColumns = {"id"}, childColumns = {"groupId"})
    private int groupId;

    private Integer friendshipId;

    private Integer expenseBundleId;

    private String description;

    private boolean repeats;

    private String repeatInterval;

    private boolean emailReminder;

    private Integer emailReminderInAdvance;

    private String nextRepeat;

    private String details;

    private int commentsCount;

    private boolean payment;

    private String creationMethod;

    private String transactionMethod;

    private boolean transactionConfirmed;

    private Integer transactionId;

    private String cost;

    private String currencyCode;

    private String date;

    private String createdAt;

    private String updatedAt;

    @ForeignKey(entity = UserEntity.class, parentColumns = {"id"}, childColumns = {"createdByUser"})
    private int createdByUser;

    @ForeignKey(entity = UserEntity.class, parentColumns = {"id"}, childColumns = {"updatedByUser"})
    private int updatedByUser;

    private String deletedAt;

    @ForeignKey(entity = UserEntity.class, parentColumns = {"id"}, childColumns = {"deletedByUser"})
    private int deletedByUser;

    @ForeignKey(entity = ExpenseCategoryEntity.class, parentColumns = {"id"}, childColumns = {"expenseCategoryId"})
    private int expenseCategoryId;

    private String expenseLargeReceipt;

    private String expenseOriginalReceipt;

    //    private List<ExpenseUserShare> usersShare;

    public ExpenseEntity(int id, int groupId, Integer friendshipId, Integer expenseBundleId, String description, boolean repeats, String repeatInterval, boolean emailReminder, Integer emailReminderInAdvance, String nextRepeat, String details, int commentsCount, boolean payment, String creationMethod, String transactionMethod, boolean transactionConfirmed, Integer transactionId, String cost, String currencyCode, String date, String createdAt, String updatedAt, int createdByUser, int updatedByUser, String deletedAt, int deletedByUser, int expenseCategoryId, String expenseLargeReceipt, String expenseOriginalReceipt) {
        this.id = id;
        this.groupId = groupId;
        this.friendshipId = friendshipId;
        this.expenseBundleId = expenseBundleId;
        this.description = description;
        this.repeats = repeats;
        this.repeatInterval = repeatInterval;
        this.emailReminder = emailReminder;
        this.emailReminderInAdvance = emailReminderInAdvance;
        this.nextRepeat = nextRepeat;
        this.details = details;
        this.commentsCount = commentsCount;
        this.payment = payment;
        this.creationMethod = creationMethod;
        this.transactionMethod = transactionMethod;
        this.transactionConfirmed = transactionConfirmed;
        this.transactionId = transactionId;
        this.cost = cost;
        this.currencyCode = currencyCode;
        this.date = date;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdByUser = createdByUser;
        this.updatedByUser = updatedByUser;
        this.deletedAt = deletedAt;
        this.deletedByUser = deletedByUser;
        this.expenseCategoryId = expenseCategoryId;
        this.expenseLargeReceipt = expenseLargeReceipt;
        this.expenseOriginalReceipt = expenseOriginalReceipt;
    }

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

    public String getDate() {
        return date;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public int getCreatedByUser() {
        return createdByUser;
    }

    public int getUpdatedByUser() {
        return updatedByUser;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public int getDeletedByUser() {
        return deletedByUser;
    }

    public int getExpenseCategoryId() {
        return expenseCategoryId;
    }

    public String getExpenseLargeReceipt() {
        return expenseLargeReceipt;
    }

    public ExpenseEntity setExpenseLargeReceipt(String expenseLargeReceipt) {
        this.expenseLargeReceipt = expenseLargeReceipt;
        return this;
    }

    public String getExpenseOriginalReceipt() {
        return expenseOriginalReceipt;
    }

    public ExpenseEntity setExpenseOriginalReceipt(String expenseOriginalReceipt) {
        this.expenseOriginalReceipt = expenseOriginalReceipt;
        return this;
    }

    public ExpenseEntity setId(int id) {
        this.id = id;
        return this;
    }

    public ExpenseEntity setGroupId(int groupId) {
        this.groupId = groupId;
        return this;
    }

    public ExpenseEntity setFriendshipId(Integer friendshipId) {
        this.friendshipId = friendshipId;
        return this;
    }

    public ExpenseEntity setExpenseBundleId(Integer expenseBundleId) {
        this.expenseBundleId = expenseBundleId;
        return this;
    }

    public ExpenseEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public ExpenseEntity setRepeats(boolean repeats) {
        this.repeats = repeats;
        return this;
    }

    public ExpenseEntity setRepeatInterval(String repeatInterval) {
        this.repeatInterval = repeatInterval;
        return this;
    }

    public ExpenseEntity setEmailReminder(boolean emailReminder) {
        this.emailReminder = emailReminder;
        return this;
    }

    public ExpenseEntity setEmailReminderInAdvance(Integer emailReminderInAdvance) {
        this.emailReminderInAdvance = emailReminderInAdvance;
        return this;
    }

    public ExpenseEntity setNextRepeat(String nextRepeat) {
        this.nextRepeat = nextRepeat;
        return this;
    }

    public ExpenseEntity setDetails(String details) {
        this.details = details;
        return this;
    }

    public ExpenseEntity setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
        return this;
    }

    public ExpenseEntity setPayment(boolean payment) {
        this.payment = payment;
        return this;
    }

    public ExpenseEntity setCreationMethod(String creationMethod) {
        this.creationMethod = creationMethod;
        return this;
    }

    public ExpenseEntity setTransactionMethod(String transactionMethod) {
        this.transactionMethod = transactionMethod;
        return this;
    }

    public ExpenseEntity setTransactionConfirmed(boolean transactionConfirmed) {
        this.transactionConfirmed = transactionConfirmed;
        return this;
    }

    public ExpenseEntity setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public ExpenseEntity setCost(String cost) {
        this.cost = cost;
        return this;
    }

    public ExpenseEntity setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    public ExpenseEntity setDate(String date) {
        this.date = date;
        return this;
    }

    public ExpenseEntity setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public ExpenseEntity setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public ExpenseEntity setCreatedByUser(int createdByUser) {
        this.createdByUser = createdByUser;
        return this;
    }

    public ExpenseEntity setUpdatedByUser(int updatedByUser) {
        this.updatedByUser = updatedByUser;
        return this;
    }

    public ExpenseEntity setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }

    public ExpenseEntity setDeletedByUser(int deletedByUser) {
        this.deletedByUser = deletedByUser;
        return this;
    }

    public ExpenseEntity setExpenseCategoryId(int expenseCategoryId) {
        this.expenseCategoryId = expenseCategoryId;
        return this;
    }
}
