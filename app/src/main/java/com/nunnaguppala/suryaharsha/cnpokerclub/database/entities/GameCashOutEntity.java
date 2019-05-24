package com.nunnaguppala.suryaharsha.cnpokerclub.database.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

@Entity(tableName = "gameCashOut", primaryKeys = {"gameId", "userId"})
public class GameCashOutEntity {
    @ForeignKey(entity = GameEntity.class, parentColumns = {"id"}, childColumns = {"gameId"})
    private int gameId;

    @ForeignKey(entity = UserEntity.class, parentColumns = {"id"}, childColumns = {"userId"})
    private int userId;

    private int cashOut;

    private String cashOutTime;

    public GameCashOutEntity(int gameId, int userId, int cashOut, String cashOutTime) {
        this.gameId = gameId;
        this.userId = userId;
        this.cashOut = cashOut;
        this.cashOutTime = cashOutTime;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCashOut() {
        return cashOut;
    }

    public void setCashOut(int cashOut) {
        this.cashOut = cashOut;
    }

    public String getCashOutTime() {
        return cashOutTime;
    }

    public void setCashOutTime(String cashOutTime) {
        this.cashOutTime = cashOutTime;
    }
}
