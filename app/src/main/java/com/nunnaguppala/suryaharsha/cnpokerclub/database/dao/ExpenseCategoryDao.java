package com.nunnaguppala.suryaharsha.cnpokerclub.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model.ExpenseCategory;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.ExpenseCategoryEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.ExpenseEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.GroupEntity;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ExpenseCategoryDao {
    @Insert(onConflict = REPLACE)
    void insert(ExpenseCategoryEntity expenseCategory);

    @Update
    void update(ExpenseCategoryEntity... expenseCategories);

    @Delete
    void delete(ExpenseCategoryEntity... expenseCategories);

    @Query("SELECT * FROM expenseCategory")
    LiveData<List<ExpenseCategoryEntity>> getAllExpenseCategories();

    @Query("SELECT * FROM expense WHERE expenseCategoryId=:expenseCategoryId")
    LiveData<List<ExpenseEntity>> getAllExpensesInCategory(int expenseCategoryId);
}
