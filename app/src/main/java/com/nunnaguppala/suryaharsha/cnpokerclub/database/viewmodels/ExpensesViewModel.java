package com.nunnaguppala.suryaharsha.cnpokerclub.database.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.ExpenseEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.pojos.ExpenseUserShareAndDetails;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.repositories.ExpenseRepository;

import java.util.List;

import javax.inject.Inject;

public class ExpensesViewModel extends ViewModel{
    private ExpenseRepository expenseRepository;

    @Inject
    public ExpensesViewModel(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public LiveData<List<ExpenseEntity>> getAllExpensesInGroup(int groupId) {
        return expenseRepository.getAllExpensesInGroup(groupId);
    }

    public LiveData<List<ExpenseUserShareAndDetails>> getAllExpenseSharesForUsersInGroup(int groupId){
        return expenseRepository.getUsersAndExpenseSharesInGroup(groupId);
    }
}
