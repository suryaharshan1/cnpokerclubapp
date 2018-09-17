package com.nunnaguppala.suryaharsha.cnpokerclub;

/**
 * Created by surya on 09/06/18.
 */
import com.nunnaguppala.suryaharsha.cnpokerclub.fragments.DefaultGroupSelectionFragment;
import com.nunnaguppala.suryaharsha.cnpokerclub.fragments.UsersFragment;

import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = PokerClubModule.class)
public interface PokerClubComponent {
    void inject(MainActivity mainActivity);
    void inject(DefaultGroupSelectionFragment fragment);
    void inject(UsersFragment fragment);
}
