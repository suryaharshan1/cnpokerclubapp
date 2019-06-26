package com.nunnaguppala.suryaharsha.cnpokerclub.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.GameBuyInEntity;

import java.util.List;

@Dao
public interface GameBuyInDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(GameBuyInEntity gameBuyInEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(GameBuyInEntity... gameBuyInEntities);

    @Update
    void update(GameBuyInEntity... gameBuyInEntities);

    @Delete
    void delete(GameBuyInEntity... gameBuyInEntities);

    @Query("select * from gameBuyIn where gameId=:gameId")
    LiveData<List<GameBuyInEntity>> getGameInfo(int gameId);

    @Query("select * from gameBuyIn where gameId=:gameId and userId=:userId")
    LiveData<List<GameBuyInEntity>> getGameInfoForUser(int gameId, int userId);

    @Query("select * from gameBuyIn where userId=:userId")
    LiveData<List<GameBuyInEntity>> getUserBuyInInfo(int userId);
}
