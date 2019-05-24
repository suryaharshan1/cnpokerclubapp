package com.nunnaguppala.suryaharsha.cnpokerclub.database.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.GameEntity;
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

    public LiveData<List<GameEntity>> getGameInfo(int gameId){
        return gameRepository.getGameInfo(gameId);
    }
}
