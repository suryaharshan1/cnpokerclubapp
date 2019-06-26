package com.nunnaguppala.suryaharsha.cnpokerclub.database.repositories;

import android.arch.lifecycle.LiveData;

import com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.Splitwise;
import com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model.Expense;
import com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model.ExpenseRepayment;
import com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model.ExpenseUserShare;
import com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model.ListExpenses;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.PokerClubDatabase;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.dao.ExpenseCategoryDao;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.dao.ExpenseDao;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.dao.ExpenseRepaymentDao;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.dao.ExpenseUserShareDao;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.ExpenseCategoryEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.ExpenseEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.ExpenseRepaymentEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.ExpenseUserShareEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.GameFilterEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.pojos.ExpenseUserShareAndDetails;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

public class ExpenseRepository {
    private final Splitwise splitwise;
    private final PokerClubDatabase database;
    private final Executor executor;

    @Inject
    public ExpenseRepository(Splitwise splitwise, PokerClubDatabase database, Executor executor) {
        this.splitwise = splitwise;
        this.database = database;
        this.executor = executor;
    }

    public LiveData<List<ExpenseEntity>> getAllExpensesInGroup(int groupId){
        refreshExpensesInGroup(groupId);
        return database.getExpenseDao().getAllExpensesInGroup(groupId);
    }

    public LiveData<List<ExpenseUserShareAndDetails>> getUsersAndExpenseSharesInGroup(int groupId) {
        refreshExpensesInGroup(groupId);
        return database.getExpenseUserShareDao().getAllExpenseSharesForUsersInGroup(groupId);
    }

    public LiveData<List<ExpenseEntity>> getUncategorisedExpensesInGroup(int groupId){
        refreshExpensesInGroup(groupId);
        return database.getExpenseDao().getUncategorisedExpensesInGroup(groupId);
    }

    public LiveData<List<ExpenseEntity>> getFilteredExpensesInGroup(int groupId){
        refreshExpensesInGroup(groupId);
        return database.getExpenseDao().getFilteredExpensesInGroup(groupId);
    }

    public void addExpenseFilter(int expenseId, boolean gameFlag) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                database.getGameFilterDao().insert(new GameFilterEntity(expenseId, gameFlag));
            }
        });
    }

    private void refreshExpensesInGroup(final int groupId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                ExpenseDao expenseDao = database.getExpenseDao();
                ExpenseCategoryDao expenseCategoryDao = database.getExpenseCategoryDao();
                ExpenseRepaymentDao expenseRepaymentDao = database.getExpenseRepaymentDao();
                ExpenseUserShareDao expenseUserShareDao = database.getExpenseUserShareDao();
                ListExpenses listExpenses = null;
                try {
                    Splitwise.Expenses.ListExpensesRequest request = splitwise.expenses().listExpenses(groupId);
                    listExpenses = request.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (listExpenses == null){
                    return;
                }
                List<Expense> expenses =  listExpenses.getExpenses();
                if(expenses == null) {
                    return;
                }
                for(Expense expense: expenses){
                    if(expense.getDeletedBy() != null && expense.getDeletedBy().getId() != 0){
                        continue;
                    }
                    expenseCategoryDao.insert(new ExpenseCategoryEntity(expense.getCategory().getId(),
                            expense.getCategory().getName()));
                    expenseDao.insert(new ExpenseEntity(expense.getId(),
                            expense.getGroupId(), expense.getFriendshipId(), expense.getExpenseBundleId(),
                            expense.getDescription(), expense.isRepeats(), expense.getRepeatInterval(),
                            expense.isEmailReminder(), expense.getEmailReminderInAdvance(), expense.getNextRepeat(),
                            expense.getDetails(), expense.getCommentsCount(), expense.isPayment(),
                            expense.getCreationMethod(), expense.getTransactionMethod(), expense.isTransactionConfirmed(),
                            expense.getTransactionId(), expense.getCost(), expense.getCurrencyCode(),
                            expense.getDate(), expense.getCreatedAt(), expense.getUpdatedAt(),
                            expense.getCreatedBy().getId(), expense.getUpdatedBy().getId(),
                            expense.getDeletedAt(), expense.getDeletedBy().getId(),
                            expense.getCategory().getId(), expense.getReceipt().getLargeReceipt(),
                            expense.getReceipt().getOriginalReceipt()));
                    if(expense.getRepayments() != null) {
                        for(ExpenseRepayment expenseRepayment : expense.getRepayments()){
                            expenseRepaymentDao.insert(new ExpenseRepaymentEntity(expenseRepayment.getFromUser(),
                                    expenseRepayment.getToUser(), expenseRepayment.getAmount(),
                                    expense.getId()));
                        }
                    }
                    if(expense.getUsersShare() != null) {
                        for(ExpenseUserShare expenseUserShare : expense.getUsersShare()) {
                            expenseUserShareDao.insert(new ExpenseUserShareEntity(
                                    expenseUserShare.getUserId(),
                                    expense.getId(),
                                    expenseUserShare.getPaidShare(),
                                    expenseUserShare.getOwedShare(),
                                    expenseUserShare.getNetBalance()
                            ));
                        }
                    }
                }
            }
        });
    }


}
