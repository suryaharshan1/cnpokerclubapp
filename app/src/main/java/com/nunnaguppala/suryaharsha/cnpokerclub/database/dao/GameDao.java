package com.nunnaguppala.suryaharsha.cnpokerclub.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomWarnings;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.GameBuyInEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.GameCashOutEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.GameEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.UserEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.pojos.UserTotalBuyIn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Dao
@TypeConverters(GameDao.Converters.class)
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

    @Query("select * from game where game_id=:id LIMIT 1")
    LiveData<GameEntity> getGameInfo(int id);

    @Query("select * from user where id not in (select distinct userId from gameBuyIn where gameId=:gameId) and id in (select distinct userId from groupUser where groupId=:groupId)")
    LiveData<List<UserEntity>> getNonPlayerListForGame(int gameId, int groupId);

    @Query("select * from user where id in (select distinct userId from gameBuyIn where gameId=:gameId)")
    LiveData<List<UserEntity>> getPlayerListForGame(long gameId);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("select * from game inner join user on game_cashierUserId=id where game_id=:gameId")
    LiveData<UserEntity> getCashierForGame(long gameId);

    @Query("update game set game_cashierUserId=:cashierUserId where game_id=:gameId")
    void setCashierForGame(long cashierUserId, long gameId);

    @Transaction
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("select *, u.totalBuyIn as totalBuyIn, GROUP_CONCAT(t.gameBuyInEntity, '^^') as gameBuyInEntities from (select user.*, game.*, (gameBuyIn.buyInID ||  '^_' || gameBuyIn.gameId || '^_' || gameBuyIn.userId || '^_' || gameBuyIn.buyIn || '^_' || gameBuyIn.buyInTime) as gameBuyInEntity from gameBuyIn inner join game on game_id=gameId inner join user on userId=id  where gameId=:gameId) as t, (select userId, SUM(buyIn) as totalBuyIn from gameBuyIn where gameId=:gameId group by userId) as u where t.id=u.userId group by t.id")
    LiveData<List<UserTotalBuyIn>> getUsersBuyInForGame(long gameId);

    class Converters {
        @TypeConverter
        public List<GameBuyInEntity> listGameBuyInFromConcatString(String value){
            if(value == null || value.isEmpty()){
                return Collections.EMPTY_LIST;
            } else {
                List<GameBuyInEntity> gameBuyInEntities = new ArrayList<GameBuyInEntity>();
                String[] rows = value.split("\\^\\^");
                for(String row : rows){
                    String[] cols = row.split("\\^_");
                    int buyInID = (cols[0] == null || cols[0].isEmpty())?-1:Integer.parseInt(cols[0]);
                    int gameId = (cols[1] == null || cols[1].isEmpty())?-1:Integer.parseInt(cols[1]);
                    int userId = (cols[2] == null || cols[2].isEmpty())?-1:Integer.parseInt(cols[2]);
                    int buyIn = (cols[3] == null || cols[3].isEmpty())?0:Integer.parseInt(cols[3]);
                    String buyInTime = cols[4];
                    gameBuyInEntities.add(new GameBuyInEntity(buyInID, gameId, userId, buyIn, buyInTime));

                }
                return gameBuyInEntities;
            }
        }
    }
}
