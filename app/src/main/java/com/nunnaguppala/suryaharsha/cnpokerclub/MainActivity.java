package com.nunnaguppala.suryaharsha.cnpokerclub;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.ExpenseEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.fragments.DefaultGroupSelectionFragment;
import com.nunnaguppala.suryaharsha.cnpokerclub.fragments.ExpenseFilterFragment;
import com.nunnaguppala.suryaharsha.cnpokerclub.fragments.LoginFragment;
import com.nunnaguppala.suryaharsha.cnpokerclub.fragments.UsersFragment;

public class MainActivity extends AppCompatActivity implements DefaultGroupSelectionFragment.OnFragmentInteractionListener,
        UsersFragment.OnFragmentInteractionListener, LoginFragment.OnLoginFragmentInteractionListener,
        ExpenseFilterFragment.OnListFragmentInteractionListener {

    private int defaultGroupID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((PokerClubApplication)getApplication()).getPokerClubComponent().inject(this);
        FragmentManager fm = getSupportFragmentManager();
        SharedPreferences sharedPreferences = getSharedPreferences(PokerClubConstants.APPLICATION_PREF_FILE, MODE_PRIVATE);
        defaultGroupID = sharedPreferences.getInt(PokerClubConstants.DEFAULT_GROUP_ID_PREF_KEY, -1);
        boolean isLoggedIn = sharedPreferences.getBoolean(PokerClubConstants.IS_LOGGED_IN_KEY, false);
        if(!isLoggedIn){
            LoginFragment loginFragment = new LoginFragment();
            fm.beginTransaction().add(R.id.content_main, loginFragment).commit();
        }
        else if (defaultGroupID == -1){
            DefaultGroupSelectionFragment list = new DefaultGroupSelectionFragment();
            fm.beginTransaction().add(R.id.content_main, list).commit();
        }
        else {
            UsersFragment usersFragment = UsersFragment.newInstance(defaultGroupID);
            fm.beginTransaction().add(R.id.content_main, usersFragment).commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_list_games:
                Intent intent = new Intent(this, ItemListActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_game_filter:
                ExpenseFilterFragment expenseFilterFragment = ExpenseFilterFragment.newInstance(defaultGroupID);
                getSupportFragmentManager().beginTransaction().replace(
                        R.id.content_main, expenseFilterFragment
                ).addToBackStack(null).commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDefaultGroupSelectionFragmentInteraction(int groupId) {
        UsersFragment usersFragment = UsersFragment.newInstance(groupId);
        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, usersFragment).commit();
    }

    @Override
    public void onUsersFragmentInteraction(Uri uri) {

    }

    @Override
    public void onLoginFragmentInteraction() {
        DefaultGroupSelectionFragment defaultGroupSelectionFragment =
                DefaultGroupSelectionFragment.newInstance();
        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_main, defaultGroupSelectionFragment).commit();
    }

    @Override
    public void onListFragmentInteraction(ExpenseEntity item) {

    }
}
