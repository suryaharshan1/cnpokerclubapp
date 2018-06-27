package com.nunnaguppala.suryaharsha.cnpokerclub.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.UserBalanceEntity;

import java.util.List;

@Dao
public interface UserBalanceDao {
    @Insert
    void insert(UserBalanceEntity user);

    @Update
    void update(UserBalanceEntity... users);

    @Delete
    void delete(UserBalanceEntity... users);

    @Query("SELECT * FROM userBalance WHERE userId=:userId")
    List<UserBalanceEntity> getBalancesForUser(final int userId);

    @Query("SELECT * FROM userBalance")
    List<UserBalanceEntity> getAllBalances();
}
