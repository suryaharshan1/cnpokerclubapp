package com.nunnaguppala.suryaharsha.cnpokerclub.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.ExpenseUserShareEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.pojos.ExpenseUserShareAndDetails;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ExpenseUserShareDao {
    @Insert(onConflict = REPLACE)
    void insert(ExpenseUserShareEntity expenseUserShare);

    @Update
    void update(ExpenseUserShareEntity... expenseUserShares);

    @Delete
    void delete(ExpenseUserShareEntity... expenseUserShares);

    @Query("SELECT * FROM expenseUserShare")
    LiveData<List<ExpenseUserShareEntity>> getAllExpenseUserShares();

    @Query("SELECT * FROM expenseUserShare WHERE expenseId=:expenseId")
    LiveData<List<ExpenseUserShareEntity>> getAllUserSharesInExpense(int expenseId);

    @Query("SELECT * FROM expenseUserShare WHERE userId=:userId")
    LiveData<List<ExpenseUserShareEntity>> getAllExpenseSharesForUser(int userId);

    @Query("SELECT * FROM user WHERE id IN (SELECT userId FROM groupUser WHERE groupId=:groupId)")
    LiveData<List<ExpenseUserShareAndDetails>> getAllExpenseSharesForUsersInGroup(int groupId);
}
