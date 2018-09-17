package com.nunnaguppala.suryaharsha.cnpokerclub.database.repositories;

import android.arch.lifecycle.LiveData;

import com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.Splitwise;
import com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model.Balance;
import com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model.Group;
import com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model.Member;
import com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model.OriginalDebt;
import com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model.SimplifiedDebt;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.PokerClubDatabase;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.dao.GroupDao;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.dao.GroupUserDao;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.dao.OriginalDebtDao;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.dao.SimplifiedDebtDao;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.dao.UserBalanceDao;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.dao.UserDao;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.GroupEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.GroupUserEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.OriginalDebtEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.SimplifiedDebtEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.UserBalanceEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.UserEntity;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

public class UserRepository {
    private final PokerClubDatabase database;
    private final Splitwise splitwise;
    private final Executor executor;

    @Inject
    public UserRepository(PokerClubDatabase database, Splitwise splitwise, Executor executor) {
        this.database = database;
        this.splitwise = splitwise;
        this.executor = executor;
    }

    public LiveData<List<UserEntity>> getAllUsersInGroup(int groupId){
        refreshUsersInGroup(groupId);
        return database.getGroupDao().getUsersInGroup(groupId);
    }

    private void refreshUsersInGroup(final int groupId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                GroupDao groupDao = database.getGroupDao();
                UserDao userDao = database.getUserDao();
                UserBalanceDao userBalanceDao = database.getUserBalanceDao();
                GroupUserDao groupUserDao = database.getGroupUserDao();
                OriginalDebtDao originalDebtDao = database.getOriginalDebtDao();
                SimplifiedDebtDao simplifiedDebtDao = database.getSimplifiedDebtDao();

                Group group = null;
                try {
                    Splitwise.Groups.GetGroupRequest request = splitwise.groups().getGroup(groupId);
                    group = request.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(group == null) {
                    return;
                }
                groupDao.insert(new GroupEntity(group.getId(),
                        group.getName(),
                        group.isSimplifiedByDefault(),
                        group.getWhiteboard(),
                        group.getGroupType(),
                        group.getInviteLink()));
                if(group.getMembers() != null) {
                    for (Member member : group.getMembers()) {
                        userDao.insert(new UserEntity(member.getId(),
                                member.getFirstName(),
                                member.getLastName(),
                                member.getEmail(),
                                member.getRegistrationStatus(),
                                member.getPicture().getSmall(),
                                member.getPicture().getMedium(),
                                member.getPicture().getLarge()));
                        if(member.getBalances() != null) {
                            for (Balance balance : member.getBalances()) {
                                userBalanceDao.insert(new UserBalanceEntity(balance.getAmount(),
                                        balance.getCurrencyCode(), member.getId()));
                            }
                        }
                        groupUserDao.insert(new GroupUserEntity(member.getId(), group.getId()));
                    }
                }
                if(group.getOriginalDebts() != null) {
                    for (OriginalDebt originalDebt : group.getOriginalDebts()) {
                        originalDebtDao.insert(new OriginalDebtEntity(originalDebt.getFrom(),
                                originalDebt.getTo(),
                                originalDebt.getAmount(),
                                originalDebt.getCurrencyCode(),
                                group.getId()));
                    }
                }
                if(group.getSimplifiedDebts() != null) {
                    for (SimplifiedDebt simplifiedDebt : group.getSimplifiedDebts()) {
                        simplifiedDebtDao.insert(new SimplifiedDebtEntity(simplifiedDebt.getFrom(),
                                simplifiedDebt.getFrom(),
                                simplifiedDebt.getAmount(),
                                simplifiedDebt.getCurrencyCode(),
                                group.getId()));
                    }
                }
            }
        });
    }
}
