package com.nunnaguppala.suryaharsha.cnpokerclub.database.repositories;

import android.arch.lifecycle.LiveData;
import android.text.TextUtils;
import android.util.Log;

import com.google.api.client.auth.oauth2.TokenErrorResponse;
import com.google.api.client.auth.oauth2.TokenResponseException;
import com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.Splitwise;
import com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model.Balance;
import com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model.Group;
import com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model.ListGroups;
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
import com.wuman.android.auth.oauth2.explicit.LenientTokenResponseException;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

public class GroupRepository {
    private final PokerClubDatabase database;
    private final Executor executor;
    private final Splitwise splitwise;

    @Inject
    public GroupRepository(Splitwise splitwise, PokerClubDatabase database, Executor executor) {
        this.database = database;
        this.executor = executor;
        this.splitwise = splitwise;
    }

    public LiveData<List<GroupEntity>> getAllGroups() {
        refreshGroups();
        return database.getGroupDao().getAllGroups();
    }

    private void refreshGroups() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                GroupDao groupDao = database.getGroupDao();
                UserDao userDao = database.getUserDao();
                UserBalanceDao userBalanceDao = database.getUserBalanceDao();
                OriginalDebtDao originalDebtDao = database.getOriginalDebtDao();
                SimplifiedDebtDao simplifiedDebtDao = database.getSimplifiedDebtDao();
                GroupUserDao groupUserDao = database.getGroupUserDao();

                ListGroups listGroups = null;
                try {
                    Splitwise.Groups.ListGroupsRequest request = splitwise.groups().listGroups();
                    listGroups = request.execute();
                } catch (Exception e) {
                    StackTraceElement t = e.getStackTrace()[0];
                    e.printStackTrace();
                    String errorMessage;
                    if (e instanceof LenientTokenResponseException) {
                        LenientTokenResponseException tre = (LenientTokenResponseException) e;
                        TokenErrorResponse errorResponse = tre.getDetails();
                        errorMessage = errorResponse.getError();
                        if (!TextUtils.isEmpty(errorResponse.getErrorDescription())) {
                            errorMessage += (": " + errorResponse.getErrorDescription());
                        }
                    } else if (e instanceof TokenResponseException) {
                        TokenResponseException tre = (TokenResponseException) e;
                        TokenErrorResponse errorResponse = tre.getDetails();
                        errorMessage = errorResponse.getError();
                        if (!TextUtils.isEmpty(errorResponse.getErrorDescription())) {
                            errorMessage += (": " + errorResponse.getErrorDescription());
                        }
                    } else {
                        errorMessage = e.getMessage();
                    }
                    errorMessage += ("\n"
                            + e.getClass().getName() + " at " + t.getClassName()
                            + "(" + t.getFileName() + ":" + t.getLineNumber() + ")");
                    Log.e("GroupRepository", errorMessage);
                }
                if (listGroups == null)
                    return;
                for (Group group : listGroups.getGroups()) {
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
            }
        });
    }


}
