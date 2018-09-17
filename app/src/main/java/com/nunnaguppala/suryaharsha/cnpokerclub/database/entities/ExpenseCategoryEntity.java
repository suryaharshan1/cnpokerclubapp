package com.nunnaguppala.suryaharsha.cnpokerclub.database.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "expenseCategory")
public class ExpenseCategoryEntity {
    @PrimaryKey
    private int id;

    private String name;

    public ExpenseCategoryEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public ExpenseCategoryEntity setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ExpenseCategoryEntity setName(String name) {
        this.name = name;
        return this;
    }
}
