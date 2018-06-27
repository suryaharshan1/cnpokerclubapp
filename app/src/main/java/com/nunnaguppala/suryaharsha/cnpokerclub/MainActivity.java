package com.nunnaguppala.suryaharsha.cnpokerclub;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson.JacksonFactory;
import com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.Splitwise;
import com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.SplitwiseRequestInitializer;
import com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model.ListGroups;
import com.nunnaguppala.suryaharsha.cnpokerclub.fragments.DefaultGroupSelectionFragment;
import com.nunnaguppala.suryaharsha.cnpokerclub.fragments.SplitwiseGroupListFragment;
import com.wuman.android.auth.AuthorizationFlow;
import com.wuman.android.auth.AuthorizationUIController;
import com.wuman.android.auth.DialogFragmentController;
import com.wuman.android.auth.OAuthManager;
import com.wuman.android.auth.oauth2.store.SharedPreferencesCredentialStore;

import java.io.IOException;

import javax.inject.Inject;


public class MainActivity extends FragmentActivity implements DefaultGroupSelectionFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm = getSupportFragmentManager();

//        if (fm.findFragmentById(android.R.id.content) == null) {
//            SplitwiseGroupListFragment list = new SplitwiseGroupListFragment();
            DefaultGroupSelectionFragment list = new DefaultGroupSelectionFragment();
            fm.beginTransaction().add(R.id.content_main, list).commit();
//        }


    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
