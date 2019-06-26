package com.nunnaguppala.suryaharsha.cnpokerclub.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.ExpenseEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.GameFilterEntity;

import java.util.List;

@Dao
public interface GameFilterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(GameFilterEntity gameFilterEntity);

    @Update
    void update(GameFilterEntity... gameFilterEntities);

    @Delete
    void delete(GameFilterEntity... gameFilterEntities);

    @Query("select * from expense where id not in (select expenseId from expenseFilter where verified=1)")
    LiveData<List<ExpenseEntity>> getAllUncategorisedExpenses();

    @Query("select * from expense where id in (select expenseId from expenseFilter where gameFlag=1)")
    LiveData<List<ExpenseEntity>> getAllFilteredExpenses();

    @Query("select * from expense where id in (select expenseId from expenseFilter where gameFlag=0)")
    LiveData<List<ExpenseEntity>> getAllNonGameExpenses();

    @Query("select * from expense where id not in (select expenseId from expenseFilter where verified=0) and groupId=:groupId")
    LiveData<List<ExpenseEntity>> getUncategorisedExpensesInGroup(int groupId);

    @Query("select * from expense where id in (select expenseId from expenseFilter where gameFlag=1) and groupId=:groupId")
    LiveData<List<ExpenseEntity>> getFilteredExpensesInGroup(int groupId);

    @Query("select * from expense where id in (select expenseId from expenseFilter where gameFlag=0) and groupId=:groupId")
    LiveData<List<ExpenseEntity>> getAllNonGameExpensesInGroup(int groupId);

}
