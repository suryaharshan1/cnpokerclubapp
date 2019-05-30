package com.nunnaguppala.suryaharsha.cnpokerclub.database.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.GameEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.UserEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.pojos.UserTotalBuyIn;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.repositories.GameRepository;

import java.util.List;

import javax.inject.Inject;

public class GameViewModel extends ViewModel {
    private LiveData<List<GameEntity>> games;
    private GameRepository gameRepository;

    @Inject
    public GameViewModel(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public void init() {
        if(this.games != null) {
            return;
        }
        this.games = gameRepository.getAllGames();
    }

    public LiveData<List<GameEntity>> getAllGames() {
        return this.games;
    }

    public Long createNewGame(GameEntity gameEntity){
        return gameRepository.createNewGame(gameEntity);
    }

    public LiveData<GameEntity> getGameInfo(int gameId){
        return gameRepository.getGameInfo(gameId);
    }

    public LiveData<List<UserEntity>> getNonPlayerListForGame(int gameId, int groupId){
        return gameRepository.getNonPlayerListForGame(gameId, groupId);
    }

    public LiveData<List<UserEntity>> getPlayerListForGame(long gameId) {
        return gameRepository.getPlayerListForGame(gameId);
    }

    public void onBoardUsersToGame(int gameId, List<Integer> userIds) {
        gameRepository.onBoardUsersToGame(gameId, userIds);
    }

    public LiveData<List<UserTotalBuyIn>> getUsersBuyInForGame(long gameId) {
        return gameRepository.getUsersBuyInForGame(gameId);
    }

    public void addBuyInForUserInGame(int gameId, int userId, int buyIn) {
        gameRepository.addBuyInForUserInGame(gameId, userId, buyIn);
    }

    public void addCashOutForUserInGame(int gameId, int userId, int cashOut) {
        gameRepository.addCashOutForUserInGame(gameId, userId, cashOut);
    }

    public LiveData<UserEntity> getCashierForGame(long gameId) {
        return gameRepository.getCashierForGame(gameId);
    }

    public void setCashierForGame(long cashierUserId, long gameId) {
        gameRepository.setCashierForGame(cashierUserId, gameId);
    }
}
