package com.nunnaguppala.suryaharsha.cnpokerclub;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.nunnaguppala.suryaharsha.cnpokerclub.fragments.DefaultGroupSelectionFragment;

public class MainActivity extends FragmentActivity implements DefaultGroupSelectionFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((PokerClubApplication)getApplication()).getPokerClubComponent().inject(this);
        FragmentManager fm = getSupportFragmentManager();
        SharedPreferences sharedPreferences = getSharedPreferences(PokerClubConstants.APPLICATION_PREF_FILE, MODE_PRIVATE);
        int defaultGroupID = sharedPreferences.getInt(PokerClubConstants.DEFAULT_GROUP_ID_PREF_KEY, -1);
        if (defaultGroupID == -1){
            DefaultGroupSelectionFragment list = new DefaultGroupSelectionFragment();
            fm.beginTransaction().add(R.id.content_main, list).commit();
        }
        else {
            //TODO attach fragment which shows the game details
        }

    }


    @Override
    public void onDefaultGroupSelectionFragmentInteraction(int groupId) {
        //TODO attach fragment which shows the game details
    }
}
