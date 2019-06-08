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

    @Query("select SUM(buyIn) as totalBuyIn from gameBuyIn where gameId=:gameId")
    LiveData<Integer> getTotalGameBuyIn(long gameId);

    @Query("select SUM(cashOut) as totalCashOut from gameCashOut where gameId=:gameId")
    LiveData<Integer> getTotalGameCashOut(long gameId);

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
    @Query("select *, GROUP_CONCAT(t.gameBuyInEntity, '^^') as gameBuyInEntities, GROUP_CONCAT(t.gameCashOutEntity, '^^') as gameCashOutEntities from (select user.*, game.*, (gameBuyIn.buyInID ||  '^_' || gameBuyIn.gameId || '^_' || gameBuyIn.userId || '^_' || gameBuyIn.buyIn || '^_' || gameBuyIn.buyInTime) as gameBuyInEntity, (gameCashOut.cashOutID || '^_' || gameCashOut.gameId || '^_' || gameCashOut.userId || '^_' || gameCashOut.cashOut || '^_' || gameCashOut.cashOutTime) as gameCashOutEntity from gameBuyIn left outer join gameCashOut on gameBuyIn.userId=gameCashOut.userId and gameBuyIn.gameId=gameCashOut.gameId inner join game on game_id=gameBuyIn.gameId inner join user on gameBuyIn.userId=id  where gameBuyIn.gameId=:gameId) as t, (select * from (select userId, ifnull(SUM(buyIn), 0) as totalBuyIn from gameBuyIn where gameId=:gameId group by userId) as x, (select y.userId, ifnull(SUM(cashOut), 0) as totalCashOut from (select distinct userId from gameBuyIn where gameId=:gameId) as y left outer join gameCashOut on y.userId=gameCashOut.userId where gameCashOut.gameId=:gameId group by y.userId) as z where x.userId=z.userId) as z where t.id=z.userId group by t.id")
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

        @TypeConverter
        public List<GameCashOutEntity> listGameCashOutFromConcatString(String value){
            if(value == null || value.isEmpty()){
                return Collections.EMPTY_LIST;
            } else {
                List<GameCashOutEntity> gameCashOutEntities = new ArrayList<GameCashOutEntity>();
                String[] rows = value.split("\\^\\^");
                for(String row : rows) {
                    String[] cols = row.split("\\^_");
                    int cashOutID = (cols[0] == null || cols[0].isEmpty())?-1:Integer.parseInt(cols[0]);
                    int gameId = (cols[1] == null || cols[1].isEmpty())?-1:Integer.parseInt(cols[1]);
                    int userId = (cols[2] == null || cols[2].isEmpty())?-1:Integer.parseInt(cols[2]);
                    int cashOut = (cols[3] == null || cols[3].isEmpty())?0:Integer.parseInt(cols[3]);
                    String cashOutTime = cols[4];
                    gameCashOutEntities.add(new GameCashOutEntity(cashOutID, gameId, userId, cashOut, cashOutTime));
                }
                return gameCashOutEntities;
            }
        }
    }
}
