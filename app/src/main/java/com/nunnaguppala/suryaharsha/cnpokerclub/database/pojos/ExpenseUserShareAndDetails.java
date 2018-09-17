package com.nunnaguppala.suryaharsha.cnpokerclub.database.pojos;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.ExpenseUserShareEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.UserEntity;

import java.util.List;

public class ExpenseUserShareAndDetails {
    @Embedded
    public UserEntity user;

    @Relation(parentColumn = "id", entityColumn = "userId")
    public List<ExpenseUserShareEntity> shares;

    public UserEntity getUser() {
        return user;
    }

    public ExpenseUserShareAndDetails setUser(UserEntity user) {
        this.user = user;
        return this;
    }

    public List<ExpenseUserShareEntity> getShares() {
        return shares;
    }

    public ExpenseUserShareAndDetails setShares(List<ExpenseUserShareEntity> shares) {
        this.shares = shares;
        return this;
    }
}
