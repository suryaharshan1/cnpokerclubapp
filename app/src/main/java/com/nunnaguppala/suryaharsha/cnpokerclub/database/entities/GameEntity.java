package com.nunnaguppala.suryaharsha.cnpokerclub.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

@Entity(tableName = "game")
public class GameEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "game_id")
    private int id;

    @ColumnInfo(name = "game_name")
    private String name;

    @ColumnInfo(name = "game_createdAt")
    private String createdAt;

    @ColumnInfo(name = "game_updatedAt")
    private String updatedAt;

    @ForeignKey(entity = UserEntity.class, parentColumns = {"id"}, childColumns = {"cashierUserId"})
    @ColumnInfo(name = "game_cashierUserId")
    private Integer cashierUserId;

    @ColumnInfo(name = "game_cashierCut")
    private int cashierCut;

    @TypeConverters(StatusConverter.class)
    @ColumnInfo(name = "game_status")
    private Status status;

    @Ignore
    private UserEntity cashier;

    public UserEntity getCashier() {
        return cashier;
    }

    public void setCashier(UserEntity cashier) {
        this.cashier = cashier;
    }

    public enum Status {
        ACTIVE(0),
        COMPLETED(1),
        PUBLISHED(2),
        DELETED(3);

        private int code;

        Status(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    public static class StatusConverter {
        @TypeConverter
        public static Status toStatus(int status) {
            switch (status) {
                case 0:
                    return Status.ACTIVE;
                case 1:
                    return Status.COMPLETED;
                case 2:
                    return Status.PUBLISHED;
                case 3:
                    return Status.DELETED;
                default:
                    throw new IllegalArgumentException("Could not recognise status");
            }
        }

        @TypeConverter
        public static int toInt(Status status) {
            return status.getCode();
        }
    }

    public GameEntity(String name, String createdAt, String updatedAt, Integer cashierUserId, int cashierCut, Status status) {
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.cashierUserId = cashierUserId;
        this.cashierCut = cashierCut;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getCashierUserId() {
        return cashierUserId;
    }

    public void setCashierUserId(Integer cashierUserId) {
        this.cashierUserId = cashierUserId;
    }

    public int getCashierCut() {
        return cashierCut;
    }

    public void setCashierCut(int cashierCut) {
        this.cashierCut = cashierCut;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
