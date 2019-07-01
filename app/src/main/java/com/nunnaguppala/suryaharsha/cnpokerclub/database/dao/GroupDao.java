package com.nunnaguppala.suryaharsha.cnpokerclub.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.GroupEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.OriginalDebtEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.SimplifiedDebtEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.UserEntity;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface GroupDao {
    @Insert(onConflict = IGNORE)
    void insert(GroupEntity user);

    @Update
    void update(GroupEntity... users);

    @Delete
    void delete(GroupEntity... users);

    @Query("SELECT * FROM `group`")
    LiveData<List<GroupEntity>> getAllGroups();

    @Query("SELECT * FROM originalDebt WHERE groupId=:groupId")
    LiveData<List<OriginalDebtEntity>> getOriginalDebtsFromGroup(final int groupId);

    @Query("SELECT * FROM simplifiedDebt WHERE groupId=:groupId")
    LiveData<List<SimplifiedDebtEntity>> getSimplifiedDebtsFromGroup(final int groupId);

    @Query("SELECT * FROM user WHERE id IN (SELECT userId FROM groupUser WHERE groupId=:groupId)")
    LiveData<List<UserEntity>> getUsersInGroup(final int groupId);

    @Query("SELECT * FROM `group` WHERE id=:groupId")
    LiveData<GroupEntity> getGroup(final int groupId);


}
