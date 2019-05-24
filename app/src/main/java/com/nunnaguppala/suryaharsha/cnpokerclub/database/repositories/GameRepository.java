package com.nunnaguppala.suryaharsha.cnpokerclub.database.repositories;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.nunnaguppala.suryaharsha.cnpokerclub.database.PokerClubDatabase;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.GameBuyInEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.GameCashOutEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.GameEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.UserEntity;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.inject.Inject;

public class GameRepository {
    private final PokerClubDatabase database;

    private final Executor executor;

    @Inject
    public GameRepository(PokerClubDatabase database, Executor executor) {
        this.database = database;
        this.executor = executor;
    }

    public LiveData<List<GameEntity>> getAllGames() {
        return database.getGameDao().getAllGames();
    }

    public LiveData<List<GameBuyInEntity>> getGameBuyInInfo(int gameId) {
        return database.getGameDao().getGameBuyInInfo(gameId);
    }

    public LiveData<List<GameCashOutEntity>> getGameCashOutInfo(int gameId) {
        return database.getGameDao().getGameCashOutInfo(gameId);
    }

    public LiveData<List<UserEntity>> getUsersInGame(int gameId){
        return database.getGameDao().getUsersInGame(gameId);
    }

    public LiveData<List<GameEntity>> getGameInfo(int gameId){
        return database.getGameDao().getGameInfo(gameId);
    }

    public long createNewGame(GameEntity gameEntity) {
        Long id = null;
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<Long> gameId = es.submit(new Callable<Long>() {
                 @Override
                 public Long call() throws Exception {
                     return database.getGameDao().insert(gameEntity);
                 }
             }
        );
        try {
            id = gameId.get();
        } catch (Exception e) {
            Log.e(GameRepository.class.getSimpleName(), "Unable retrieve gameID");
        } finally {
            es.shutdown();
        }
        return id;
    }
}
