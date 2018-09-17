package com.nunnaguppala.suryaharsha.cnpokerclub.database.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.UserEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.repositories.UserRepository;

import java.util.List;

import javax.inject.Inject;

public class UsersViewModel extends ViewModel{
    private UserRepository userRepository;

    @Inject
    public UsersViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LiveData<List<UserEntity>> getAllUsersInGroup(int groupId){
        return userRepository.getAllUsersInGroup(groupId);
    }
}
