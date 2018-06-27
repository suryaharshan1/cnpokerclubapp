package com.nunnaguppala.suryaharsha.cnpokerclub.database.entities;

import android.arch.persistence.room.Entity;

@Entity(primaryKeys = {"userId", "groupId"}, tableName = "groupUser")
public class GroupUserEntity {
    private int userId;

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
