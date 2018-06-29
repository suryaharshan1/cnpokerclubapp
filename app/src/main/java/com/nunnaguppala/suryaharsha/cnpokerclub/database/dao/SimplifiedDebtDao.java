package com.nunnaguppala.suryaharsha.cnpokerclub.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.SimplifiedDebtEntity;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface SimplifiedDebtDao {
    @Insert(onConflict = REPLACE)
    void insert(SimplifiedDebtEntity user);

    @Update
    void update(SimplifiedDebtEntity... users);

    @Delete
    void delete(SimplifiedDebtEntity... users);

    @Query("SELECT * FROM simplifiedDebt WHERE groupId=:groupId")
    LiveData<List<SimplifiedDebtEntity>> getSimplifiedDebtsFromGroup(final int groupId);

    @Query("SELECT * FROM simplifiedDebt WHERE fromUser=:userId")
    LiveData<List<SimplifiedDebtEntity>> getSimplifiedDebtsFromUser(final int userId);

    @Query("SELECT * FROM simplifiedDebt WHERE toUser=:userId")
    LiveData<List<SimplifiedDebtEntity>> getSimplifiedDebtsToUser(final int userId);

    @Query("SELECT * FROM simplifiedDebt")
    LiveData<List<SimplifiedDebtEntity>> getAllSimplifiedDebts();
}

