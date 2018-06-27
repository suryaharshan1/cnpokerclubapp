package com.nunnaguppala.suryaharsha.cnpokerclub.loaders;

import android.support.v4.app.FragmentActivity;

import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.util.Lists;
import com.nunnaguppala.suryaharsha.cnpokerclub.OAuth;
import com.nunnaguppala.suryaharsha.cnpokerclub.PokerClubConstants;
import com.nunnaguppala.suryaharsha.cnpokerclub.R;
import com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.Splitwise;
import com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.SplitwiseRequestInitializer;
import com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model.ListGroups;

import java.util.List;

public class SplitwiseGroupListLoader extends AsyncResourceLoader<ListGroups> {

    private final OAuth oauth;

    public SplitwiseGroupListLoader(FragmentActivity activity) {
        super(activity);
        oauth = OAuth.newInstance(activity.getApplicationContext(),
                activity.getSupportFragmentManager(),
                new ClientParametersAuthentication(PokerClubConstants.SPLIT_WISE_CONSUMER_KEY,
                        PokerClubConstants.SPLIT_WISE_CONSUMER_SECRET),
                PokerClubConstants.SPLIT_WISE_AUTHORIZATION_VERIFIER_SERVER_URL,
                PokerClubConstants.SPLIT_WISE_TOKEN_SERVER_URL,
                PokerClubConstants.SPLIT_WISE_REDIRECT_URL,
                Lists.<String> newArrayList(),
                PokerClubConstants.SPLIT_WISE_TEMPORARY_TOKEN_REQUEST_URL);
    }

    @Override
    public ListGroups loadResourceInBackground() throws Exception {
        Credential credential = oauth.authorize10a(getContext().getString(R.string.app_name)).getResult();

        Splitwise splitwise = new Splitwise.Builder(OAuth.HTTP_TRANSPORT, OAuth.JSON_FACTORY, credential).setApplicationName(getContext()
                .getString(R.string.app_name))
                .setSplitwiseRequestInitializer(new SplitwiseRequestInitializer())
                .build();
        Splitwise.Groups.ListGroupsRequest request = splitwise.groups().listGroups();
        ListGroups listGroups = request.execute();
        return listGroups;
    }

    @Override
    public void updateErrorStateIfApplicable(Result<ListGroups> result) {
        //update errors if applicable
    }
}
