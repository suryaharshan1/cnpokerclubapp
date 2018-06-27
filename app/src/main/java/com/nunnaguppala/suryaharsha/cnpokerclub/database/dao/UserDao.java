package com.nunnaguppala.suryaharsha.cnpokerclub.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.GroupEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.OriginalDebtEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.SimplifiedDebtEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.UserBalanceEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.UserEntity;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insert(UserEntity user);

    @Update
    void update(UserEntity... users);

    @Delete
    void delete(UserEntity... users);

    @Query("SELECT * FROM user")
    List<UserEntity> getAllUsers();

    @Query("SELECT * FROM userBalance WHERE userId=:userId")
    List<UserBalanceEntity> getBalancesForUser(final int userId);

    @Query("SELECT * FROM originalDebt WHERE fromUser=:userId")
    List<OriginalDebtEntity> getOriginalDebtsFromUser(final int userId);

    @Query("SELECT * FROM originalDebt WHERE toUser=:userId")
    List<OriginalDebtEntity> getOriginalDebtsToUser(final int userId);

    @Query("SELECT * FROM simplifiedDebt WHERE fromUser=:userId")
    List<SimplifiedDebtEntity> getSimplifiedDebtsFromUser(final int userId);

    @Query("SELECT * FROM simplifiedDebt WHERE toUser=:userId")
    List<SimplifiedDebtEntity> getSimplifiedDebtsToUser(final int userId);

    @Query("SELECT * FROM `group` WHERE EXISTS (SELECT groupId FROM groupUser WHERE userId=:userId)")
    List<GroupEntity> getGroupsForUser(final int userId);
}
