package com.nunnaguppala.suryaharsha.cnpokerclub.database.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.GroupEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.repositories.GroupRepository;

import java.util.List;

import javax.inject.Inject;

public class GroupsViewModel extends ViewModel {
    private LiveData<List<GroupEntity>> groups;
    private GroupRepository groupRepository;

    @Inject
    public GroupsViewModel(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public void init() {
        if(this.groups != null) {
            return;
        }
        this.groups = groupRepository.getAllGroups();
    }

    public LiveData<List<GroupEntity>> getAllGroups() {
        return this.groups;
    }
}
