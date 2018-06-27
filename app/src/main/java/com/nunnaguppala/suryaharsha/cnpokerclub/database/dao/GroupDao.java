package com.nunnaguppala.suryaharsha.cnpokerclub.database.dao;

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


@Dao
public interface GroupDao {
    @Insert
    void insert(GroupEntity user);

    @Update
    void update(GroupEntity... users);

    @Delete
    void delete(GroupEntity... users);

    @Query("SELECT * FROM `group`")
    List<GroupEntity> getAllGroups();

    @Query("SELECT * FROM originalDebt WHERE groupId=:groupId")
    List<OriginalDebtEntity> getOriginalDebtsFromGroup(final int groupId);

    @Query("SELECT * FROM simplifiedDebt WHERE groupId=:groupId")
    List<SimplifiedDebtEntity> getSimplifiedDebtsFromGroup(final int groupId);

    @Query("SELECT * FROM user WHERE EXISTS (SELECT userId FROM groupUser WHERE groupId=:groupId)")
    List<UserEntity> getUsersInGroup(final int groupId);
}
