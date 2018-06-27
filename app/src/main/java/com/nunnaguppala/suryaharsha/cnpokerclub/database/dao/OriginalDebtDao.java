package com.nunnaguppala.suryaharsha.cnpokerclub.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model.OriginalDebt;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.OriginalDebtEntity;

import java.util.List;

@Dao
public interface OriginalDebtDao {
    @Insert
    void insert(OriginalDebtEntity user);

    @Update
    void update(OriginalDebtEntity... users);

    @Delete
    void delete(OriginalDebtEntity... users);

    @Query("SELECT * FROM originalDebt WHERE groupId=:groupId")
    List<OriginalDebtEntity> getOriginalDebtsFromGroup(final int groupId);

    @Query("SELECT * FROM originalDebt WHERE fromUser=:userId")
    List<OriginalDebtEntity> getOriginalDebtsFromUser(final int userId);

    @Query("SELECT * FROM originalDebt WHERE toUser=:userId")
    List<OriginalDebtEntity> getOriginalDebtsToUser(final int userId);

    @Query("SELECT * FROM originalDebt")
    List<OriginalDebtEntity> getAllOriginalDebts();
}
