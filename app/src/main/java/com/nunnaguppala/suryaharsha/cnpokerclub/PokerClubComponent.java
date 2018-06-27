package com.nunnaguppala.suryaharsha.cnpokerclub;

/**
 * Created by surya on 09/06/18.
 */
import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = PokerClubModule.class)
interface PokerClubComponent {
    void inject(MainActivity mainActivity);
}
