package com.nunnaguppala.suryaharsha.cnpokerclub.database.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;

import com.nunnaguppala.suryaharsha.cnpokerclub.database.repositories.ExpenseRepository;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.repositories.GroupRepository;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.repositories.UserRepository;

import javax.inject.Inject;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private GroupRepository groupRepository;
    private UserRepository userRepository;
    private ExpenseRepository expenseRepository;
    private Context context;

    @Inject
    public ViewModelFactory(Context context, GroupRepository groupRepository, UserRepository userRepository,
                            ExpenseRepository expenseRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.expenseRepository = expenseRepository;
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(GroupsViewModel.class)) {
            return (T) new GroupsViewModel(groupRepository);
        }
        else if(modelClass.isAssignableFrom(UsersViewModel.class)) {
            return (T) new UsersViewModel(userRepository);
        }
        else if(modelClass.isAssignableFrom(ExpensesViewModel.class)){
            return (T) new ExpensesViewModel(expenseRepository);
        }
        else {
            throw new IllegalArgumentException("ViewModel not found");
        }
    }
}
