package com.nunnaguppala.suryaharsha.cnpokerclub.database.repositories;

import android.arch.lifecycle.LiveData;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.Splitwise;
import com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model.CreateExpense;
import com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model.ExpenseUserShare;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.PokerClubDatabase;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.GameBuyInEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.GameCashOutEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.GameEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.UserEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.pojos.UserTotalBuyIn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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

    private final Splitwise splitwise;

    @Inject
    public GameRepository(PokerClubDatabase database, Executor executor, Splitwise splitwise) {
        this.database = database;
        this.executor = executor;
        this.splitwise = splitwise;
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

    public LiveData<GameEntity> getGameInfo(int gameId){
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

    public void onBoardUsersToGame(int gameId, List<Integer> userIds) {
        executor.execute(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
                ArrayList<GameBuyInEntity> gameBuyInEntities = new ArrayList<>();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy,HH:mm");
                String currentTime = simpleDateFormat.format(Calendar.getInstance().getTime());
                for(Integer userId : userIds) {
                    gameBuyInEntities.add(new GameBuyInEntity(
                            gameId,
                            userId,
                            0,
                            currentTime
                    ));
                }
                database.getGameBuyInDao().insert(gameBuyInEntities.toArray(new GameBuyInEntity[gameBuyInEntities.size()]));
            }
        });
    }

    public LiveData<List<UserEntity>> getNonPlayerListForGame(int gameId, int groupId){
        return database.getGameDao().getNonPlayerListForGame(gameId, groupId);
    }

    public LiveData<List<UserEntity>> getPlayerListForGame(long gameId) {
        return database.getGameDao().getPlayerListForGame(gameId);
    }

    public LiveData<List<UserTotalBuyIn>> getUsersBuyInForGame(long gameId) {
        return database.getGameDao().getUsersBuyInForGame(gameId);
    }

    public LiveData<UserEntity> getCashierForGame(long gameId) {
        return database.getGameDao().getCashierForGame(gameId);
    }

    public LiveData<Integer> getTotalBuyInForGame(long gameId){
        return database.getGameDao().getTotalGameBuyIn(gameId);
    }

    public LiveData<Integer> getTotalCashOutForGame(long gameId){
        return database.getGameDao().getTotalGameCashOut(gameId);
    }

    public void setCashierForGame(long cashierUserId, long gameId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                database.getGameDao().setCashierForGame(cashierUserId, gameId);
            }
        });
    }

    public void setCashierForGame(GameEntity gameEntity, long cashierUserId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                gameEntity.setCashierUserId((int)cashierUserId);
                database.getGameDao().insert(gameEntity);
            }
        });
    }

    public void setCashierCutForGame(GameEntity gameEntity, int cashierCut){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                gameEntity.setCashierCut(cashierCut);
                database.getGameDao().insert(gameEntity);
            }
        });
    }

    public void addBuyInForUserInGame(int gameId, int userId, int buyIn) {
        executor.execute(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy,HH:mm");
                String currentTime = simpleDateFormat.format(Calendar.getInstance().getTime());
                database.getGameBuyInDao().insert(new GameBuyInEntity(gameId, userId, buyIn, currentTime));
            }
        });
    }

    public void addCashOutForUserInGame(int gameId, int userId, int cashOut) {
        executor.execute(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy,HH:mm");
                String currentTime = simpleDateFormat.format(Calendar.getInstance().getTime());
                database.getGameCashOutDao().insert(new GameCashOutEntity(gameId, userId, cashOut, currentTime));
            }
        });
    }

    public void syncGameWithSplitwise(GameEntity gameEntity, List<UserTotalBuyIn> usersBuyInInfo, long groupId){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                StringBuilder s = new StringBuilder("?currency_code=INR&category_id=18&creation_method=iou&");
                s.append("group_id=");
                s.append(groupId);
                s.append("&");
                s.append("cost=");
                s.append(gameEntity.getTotalBuyIn());
                s.append("&");
                s.append("description=");
                s.append(Uri.encode(gameEntity.getName()));
                int index = 0;
                for(UserTotalBuyIn userBuyIn : usersBuyInInfo) {
                    String userParamPrefix = "users__" + String.valueOf(index) + "__";
                    s.append("&");
                    s.append(userParamPrefix);
                    s.append("user_id=");
                    s.append(userBuyIn.getUser().getId());
                    s.append("&");
                    s.append(userParamPrefix);
                    s.append("paid_share=");
                    if(gameEntity.getCashier() != null && gameEntity.getCashier().getId() == userBuyIn.getUser().getId()){
                        s.append(userBuyIn.getTotalCashOut() + gameEntity.getCashierCut()*usersBuyInInfo.size());
                    }
                    else {
                        s.append(userBuyIn.getTotalCashOut());
                    }
                    s.append("&");
                    s.append(userParamPrefix);
                    s.append("owed_share=");
                    s.append(userBuyIn.getTotalBuyIn() +
                            (gameEntity.getCashier()==null?0:gameEntity.getCashierCut()));
                    index++;
                }
                try {
                    Splitwise.Expenses.CreateNewExpenseRequest request = splitwise.expenses().createNewExpense(s.toString());
                    request.execute();
                    gameEntity.setStatus(GameEntity.Status.PUBLISHED);
                    database.getGameDao().update(gameEntity);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("GameRepository", e.toString());
                    Log.e("GameRepository", e.getLocalizedMessage());
                }
            }
        });
    }
}
