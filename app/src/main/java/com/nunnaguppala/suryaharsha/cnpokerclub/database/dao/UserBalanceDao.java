package com.nunnaguppala.suryaharsha.cnpokerclub.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.UserBalanceEntity;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserBalanceDao {
    @Insert(onConflict = REPLACE)
    void insert(UserBalanceEntity user);

    @Update
    void update(UserBalanceEntity... users);

    @Delete
    void delete(UserBalanceEntity... users);

    @Query("SELECT * FROM userBalance WHERE userId=:userId")
    LiveData<List<UserBalanceEntity>> getBalancesForUser(final int userId);

    @Query("SELECT * FROM userBalance")
    LiveData<List<UserBalanceEntity>> getAllBalances();

    @Query("SELECT * FROM userBalance WHERE userId=:userId AND currencyCode LIKE '%' || :currencyCode || '%'")
    LiveData<UserBalanceEntity> getBalanceForUserAndCurrency(final int userId, final String currencyCode);
}
