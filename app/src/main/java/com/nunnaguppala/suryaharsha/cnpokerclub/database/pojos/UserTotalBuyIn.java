package com.nunnaguppala.suryaharsha.cnpokerclub.database.pojos;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.GameBuyInEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.GameCashOutEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.GameEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.UserEntity;

import java.util.List;

public class UserTotalBuyIn {

    @Embedded
    public GameEntity game;

    @Embedded
    public UserEntity user;

    public List<GameBuyInEntity> gameBuyInEntities;

    public int totalBuyIn;

    public List<GameCashOutEntity> gameCashOutEntities;

    public int totalCashOut;

    public GameEntity getGame() {
        return game;
    }

    public void setGame(GameEntity game) {
        this.game = game;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public List<GameBuyInEntity> getGameBuyInEntities() {
        return gameBuyInEntities;
    }

    public void setGameBuyInEntities(List<GameBuyInEntity> gameBuyInEntities) {
        this.gameBuyInEntities = gameBuyInEntities;
    }

    public int getTotalBuyIn() {
        return totalBuyIn;
    }

    public void setTotalBuyIn(int totalBuyIn) {
        this.totalBuyIn = totalBuyIn;
    }

    public List<GameCashOutEntity> getGameCashOutEntities() {
        return gameCashOutEntities;
    }

    public void setGameCashOutEntities(List<GameCashOutEntity> gameCashOutEntities) {
        this.gameCashOutEntities = gameCashOutEntities;
    }

    public int getTotalCashOut() {
        return totalCashOut;
    }

    public void setTotalCashOut(int totalCashOut) {
        this.totalCashOut = totalCashOut;
    }
}
