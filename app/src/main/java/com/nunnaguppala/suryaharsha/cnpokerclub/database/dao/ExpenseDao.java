package com.nunnaguppala.suryaharsha.cnpokerclub.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.ExpenseEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.ExpenseRepaymentEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.ExpenseUserShareEntity;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ExpenseDao {
    @Insert(onConflict = REPLACE)
    void insert(ExpenseEntity expense);

    @Update
    void update(ExpenseEntity... expenses);

    @Delete
    void delete(ExpenseEntity... expenses);

    @Query("SELECT * FROM expense")
    LiveData<List<ExpenseEntity>> getAllExpenses();

    @Query("SELECT * FROM expense WHERE groupId=:groupId")
    LiveData<List<ExpenseEntity>> getAllExpensesInGroup(int groupId);

    @Query("SELECT * FROM expense WHERE groupId=:groupId AND id in (SELECT expenseId FROM expenseUserShare WHERE userId=:userId)")
    LiveData<List<ExpenseEntity>> getAllExpensesForUserInGroup(int userId, int groupId);

    @Query("SELECT * FROM expenseUserShare WHERE expenseId=:expenseId")
    LiveData<List<ExpenseUserShareEntity>> getAllUserSharesInExpense(int expenseId);

    @Query("SELECT * FROM expenseRepayment WHERE expenseId=:expenseId")
    LiveData<List<ExpenseRepaymentEntity>> getAllRepaymentsInExpense(int expenseId);

    @Query("SELECT * FROM expense WHERE expenseCategoryId=:expenseCategoryId")
    LiveData<List<ExpenseEntity>> getAllExpensesInCategory(int expenseCategoryId);
}
