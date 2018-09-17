package com.nunnaguppala.suryaharsha.cnpokerclub.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.ExpenseRepaymentEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.GroupEntity;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ExpenseRepaymentDao {
    @Insert(onConflict = REPLACE)
    void insert(ExpenseRepaymentEntity expenseRepayment);

    @Update
    void update(ExpenseRepaymentEntity... expenseRepayments);

    @Delete
    void delete(ExpenseRepaymentEntity... expenseRepayments);

    @Query("SELECT * FROM expenseRepayment WHERE expenseId=:expenseId")
    LiveData<List<ExpenseRepaymentEntity>> getAllRepaymentsInExpense(int expenseId);

    @Query("SELECT * FROM expenseRepayment")
    LiveData<List<ExpenseRepaymentEntity>> getAllRepayments();
}
