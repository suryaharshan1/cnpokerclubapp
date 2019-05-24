package com.nunnaguppala.suryaharsha.cnpokerclub.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.client.util.Lists;
import com.nunnaguppala.suryaharsha.cnpokerclub.OAuth;
import com.nunnaguppala.suryaharsha.cnpokerclub.PokerClubApplication;
import com.nunnaguppala.suryaharsha.cnpokerclub.PokerClubConstants;
import com.nunnaguppala.suryaharsha.cnpokerclub.R;
import com.wuman.android.auth.AuthorizationFlow;
import com.wuman.android.auth.oauth2.store.SharedPreferencesCredentialStore;

import java.io.IOException;
import java.util.concurrent.CancellationException;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnLoginFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnLoginFragmentInteractionListener mListener;

    @Inject
    SharedPreferencesCredentialStore credentialStore;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((PokerClubApplication)(getActivity().getApplication())).getPokerClubComponent().inject(this);
        OAuth oauth = OAuth.newInstance(getContext(),
                getFragmentManager(),
                new ClientParametersAuthentication(PokerClubConstants.SPLIT_WISE_CONSUMER_KEY,
                        PokerClubConstants.SPLIT_WISE_CONSUMER_SECRET),
                PokerClubConstants.SPLIT_WISE_AUTHORIZATION_VERIFIER_SERVER_URL,
                PokerClubConstants.SPLIT_WISE_TOKEN_SERVER_URL,
                PokerClubConstants.SPLIT_WISE_REDIRECT_URL,
                Lists.<String> newArrayList(),
                PokerClubConstants.SPLIT_WISE_TEMPORARY_TOKEN_REQUEST_URL);
        new Thread(new Runnable() {
            public void run() {
                try {
                    oauth.authorize10a(getContext().getString(R.string.app_name)).getResult();
                    SharedPreferences sp = getContext().getSharedPreferences(PokerClubConstants.APPLICATION_PREF_FILE, Context.MODE_PRIVATE);
                    sp.edit().putBoolean(PokerClubConstants.IS_LOGGED_IN_KEY, true).apply();
                    mListener.onLoginFragmentInteraction();
                } catch (IOException e) {
                    Toast.makeText(getContext(), "Some exception happened, try again!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                } catch (CancellationException e) {
                    Toast.makeText(getContext(), "Login process interrupted, try again!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);
        return textView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLoginFragmentInteractionListener) {
            mListener = (OnLoginFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnLoginFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnLoginFragmentInteractionListener {
        // TODO: Update argument type and name
        void onLoginFragmentInteraction();
    }
}
