package com.nunnaguppala.suryaharsha.cnpokerclub.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.GameCashOutEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.pojos.UserTotalCashOut;

import java.util.List;

@Dao
public interface GameCashOutDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(GameCashOutEntity gameCashOutEntity);

    @Update
    void update(GameCashOutEntity... gameCashOutEntities);

    @Delete
    void delete(GameCashOutEntity... gameCashOutEntities);

    @Query("select * from gameCashOut where gameId=:gameId")
    LiveData<List<GameCashOutEntity>> getGameCashOutInfo(int gameId);

    @Query("select * from gameCashOut where gameId=:gameId and userId=:userId")
    LiveData<List<GameCashOutEntity>> getGameCashOutInfoForUser(int gameId, int userId);

    @Query("select userId, gameId, SUM(cashOut) as totalCashOut from gameCashOut where gameId=:gameId and userId=:userId")
    LiveData<UserTotalCashOut> getUserTotalCashOutInfoForGame(int gameId, int userId);
}
