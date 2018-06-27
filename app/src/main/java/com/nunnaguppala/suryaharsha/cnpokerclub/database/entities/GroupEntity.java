package com.nunnaguppala.suryaharsha.cnpokerclub.database.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "group")
public class GroupEntity {
    @PrimaryKey
    private int id;

    private String name;

    private boolean isSimplifiedByDefault;

    private String whiteboard;

    private String groupType;

    private String inviteLink;

    public GroupEntity(int id, String name, boolean isSimplifiedByDefault, String whiteboard, String groupType, String inviteLink) {
        this.id = id;
        this.name = name;
        this.isSimplifiedByDefault = isSimplifiedByDefault;
        this.whiteboard = whiteboard;
        this.groupType = groupType;
        this.inviteLink = inviteLink;
    }

    public int getId() {
        return id;
    }

    public GroupEntity setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public GroupEntity setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isSimplifiedByDefault() {
        return isSimplifiedByDefault;
    }

    public GroupEntity setSimplifiedByDefault(boolean simplifiedByDefault) {
        isSimplifiedByDefault = simplifiedByDefault;
        return this;
    }

    public String getWhiteboard() {
        return whiteboard;
    }

    public GroupEntity setWhiteboard(String whiteboard) {
        this.whiteboard = whiteboard;
        return this;
    }

    public String getGroupType() {
        return groupType;
    }

    public GroupEntity setGroupType(String groupType) {
        this.groupType = groupType;
        return this;
    }

    public String getInviteLink() {
        return inviteLink;
    }

    public GroupEntity setInviteLink(String inviteLink) {
        this.inviteLink = inviteLink;
        return this;
    }
}
