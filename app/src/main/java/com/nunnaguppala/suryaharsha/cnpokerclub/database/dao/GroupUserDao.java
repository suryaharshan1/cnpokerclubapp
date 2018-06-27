package com.nunnaguppala.suryaharsha.cnpokerclub.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.GroupEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.GroupUserEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.UserEntity;

import java.util.List;


@Dao
public interface GroupUserDao {
    @Insert
    void insert(GroupUserEntity user);

    @Update
    void update(GroupUserEntity... users);

    @Delete
    void delete(GroupUserEntity... users);

    @Query("SELECT * FROM user WHERE EXISTS (SELECT userId FROM groupUser WHERE groupId=:groupId)")
    List<UserEntity> getUsersInGroup(final int groupId);

    @Query("SELECT * FROM `group` WHERE EXISTS (SELECT groupId FROM groupUser WHERE userId=:userId)")
    List<GroupEntity> getGroupsForUser(final int userId);
}