package com.nunnaguppala.suryaharsha.cnpokerclub;

/**
 * Created by surya on 09/06/18.
 */
import android.app.Application;

public class PokerClubApplication extends Application{
    private PokerClubComponent pokerClubComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        pokerClubComponent = createPokerClubComponent();
    }

    PokerClubComponent getPokerClubComponent(){
        return pokerClubComponent;
    }

    private PokerClubComponent createPokerClubComponent() {
        return DaggerPokerClubComponent
                .builder()
                .pokerClubModule(new PokerClubModule(this))
                .build();
    }
}
