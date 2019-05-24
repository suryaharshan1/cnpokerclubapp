package com.nunnaguppala.suryaharsha.cnpokerclub.database.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

@Entity(primaryKeys = {"userId", "groupId"}, tableName = "groupUser")
public class GroupUserEntity {
    @ForeignKey(entity = UserEntity.class, parentColumns = {"id"}, childColumns = {"userId"})
    private int userId;

    @ForeignKey(entity = GroupEntity.class, parentColumns = {"id"}, childColumns = {"groupId"})
    private int groupId;

    public GroupUserEntity(int userId, int groupId) {
        this.userId = userId;
        this.groupId = groupId;
    }

    public int getUserId() {
        return userId;
    }

    public GroupUserEntity setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public int getGroupId() {
        return groupId;
    }

    public GroupUserEntity setGroupId(int groupId) {
        this.groupId = groupId;
        return this;
    }
}
