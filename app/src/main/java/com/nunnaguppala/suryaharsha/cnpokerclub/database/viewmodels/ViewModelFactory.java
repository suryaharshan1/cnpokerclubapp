package com.nunnaguppala.suryaharsha.cnpokerclub.database.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;

import com.nunnaguppala.suryaharsha.cnpokerclub.database.repositories.GroupRepository;

import javax.inject.Inject;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private GroupRepository groupRepository;
    private Context context;

    @Inject
    public ViewModelFactory(Context context, GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(GroupsViewModel.class)) {
            return (T) new GroupsViewModel(groupRepository);
        }
        else {
            throw new IllegalArgumentException("ViewModel not found");
        }
    }
}
