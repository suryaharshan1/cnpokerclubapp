package com.nunnaguppala.suryaharsha.cnpokerclub.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.GameBuyInEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.GameCashOutEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.GameEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.UserEntity;

import java.util.List;

@Dao
public interface GameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(GameEntity gameEntity);

    @Update
    void update(GameEntity... gameEntities);

    @Delete
    void delete(GameEntity... gameEntity);

    @Query("select * from game")
    LiveData<List<GameEntity>> getAllGames();

    @Query("select * from gameBuyIn where gameId=:gameId")
    LiveData<List<GameBuyInEntity>> getGameBuyInInfo(int gameId);

    @Query("select * from gameCashOut where gameId=:gameId")
    LiveData<List<GameCashOutEntity>> getGameCashOutInfo(int gameId);

    @Query("select * from user where id in (select userId from gameBuyIn where gameId=:gameId)")
    LiveData<List<UserEntity>> getUsersInGame(int gameId);

    @Query("select * from game where id=:id LIMIT 1")
    LiveData<List<GameEntity>> getGameInfo(int id);
}
