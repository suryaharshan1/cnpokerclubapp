package com.nunnaguppala.suryaharsha.cnpokerclub.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.OriginalDebtEntity;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface OriginalDebtDao {
    @Insert(onConflict = REPLACE)
    void insert(OriginalDebtEntity user);

    @Update
    void update(OriginalDebtEntity... users);

    @Delete
    void delete(OriginalDebtEntity... users);

    @Query("SELECT * FROM originalDebt WHERE groupId=:groupId")
    LiveData<List<OriginalDebtEntity>> getOriginalDebtsFromGroup(final int groupId);

    @Query("SELECT * FROM originalDebt WHERE fromUser=:userId")
    LiveData<List<OriginalDebtEntity>> getOriginalDebtsFromUser(final int userId);

    @Query("SELECT * FROM originalDebt WHERE toUser=:userId")
    LiveData<List<OriginalDebtEntity>> getOriginalDebtsToUser(final int userId);

    @Query("SELECT * FROM originalDebt")
    LiveData<List<OriginalDebtEntity>> getAllOriginalDebts();
}
