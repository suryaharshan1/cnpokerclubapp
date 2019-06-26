package com.nunnaguppala.suryaharsha.cnpokerclub.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model.ExpenseCategory;
import com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model.Group;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.dao.ExpenseCategoryDao;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.dao.ExpenseDao;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.dao.ExpenseRepaymentDao;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.dao.ExpenseUserShareDao;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.dao.GameBuyInDao;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.dao.GameCashOutDao;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.dao.GameDao;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.dao.GameFilterDao;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.dao.GroupDao;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.dao.GroupUserDao;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.dao.OriginalDebtDao;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.dao.SimplifiedDebtDao;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.dao.UserBalanceDao;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.dao.UserDao;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.ExpenseCategoryEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.ExpenseEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.ExpenseRepaymentEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.ExpenseUserShareEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.GameBuyInEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.GameCashOutEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.GameEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.GameFilterEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.GroupEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.GroupUserEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.OriginalDebtEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.SimplifiedDebtEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.UserBalanceEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.UserEntity;

@Database(entities = {GroupEntity.class,
        GroupUserEntity.class, OriginalDebtEntity.class, SimplifiedDebtEntity.class,
        UserBalanceEntity.class, UserEntity.class,
        ExpenseEntity.class, ExpenseRepaymentEntity.class,
        ExpenseCategoryEntity.class, ExpenseUserShareEntity.class,
        GameEntity.class, GameBuyInEntity.class, GameCashOutEntity.class, GameFilterEntity.class},
        version = 8, exportSchema = false)
public abstract class PokerClubDatabase extends RoomDatabase{
    public abstract GroupDao getGroupDao();
    public abstract UserDao getUserDao();
    public abstract GroupUserDao getGroupUserDao();
    public abstract OriginalDebtDao getOriginalDebtDao();
    public abstract SimplifiedDebtDao getSimplifiedDebtDao();
    public abstract UserBalanceDao getUserBalanceDao();
    public abstract ExpenseDao getExpenseDao();
    public abstract ExpenseCategoryDao getExpenseCategoryDao();
    public abstract ExpenseRepaymentDao getExpenseRepaymentDao();
    public abstract ExpenseUserShareDao getExpenseUserShareDao();
    public abstract GameDao getGameDao();
    public abstract GameBuyInDao getGameBuyInDao();
    public abstract GameCashOutDao getGameCashOutDao();
    public abstract GameFilterDao getGameFilterDao();
}
