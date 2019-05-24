package com.nunnaguppala.suryaharsha.cnpokerclub.database.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

@Entity(tableName = "gameBuyIn", primaryKeys = {"gameId", "userId"})
public class GameBuyInEntity {
    @ForeignKey(entity = GameEntity.class, parentColumns = {"id"}, childColumns = {"gameId"})
    private int gameId;

    @ForeignKey(entity = UserEntity.class, parentColumns = {"id"}, childColumns = {"userId"})
    private int userId;

    private int buyIn;

    private String buyInTime;

    public GameBuyInEntity(int gameId, int userId, int buyIn, String buyInTime) {
        this.gameId = gameId;
        this.userId = userId;
        this.buyIn = buyIn;
        this.buyInTime = buyInTime;
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

    public int getBuyIn() {
        return buyIn;
    }

    public void setBuyIn(int buyIn) {
        this.buyIn = buyIn;
    }

    public String getBuyInTime() {
        return buyInTime;
    }

    public void setBuyInTime(String buyInTime) {
        this.buyInTime = buyInTime;
    }
}
