package com.nunnaguppala.suryaharsha.cnpokerclub.fragments;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.nunnaguppala.suryaharsha.cnpokerclub.PokerClubApplication;
import com.nunnaguppala.suryaharsha.cnpokerclub.PokerClubConstants;
import com.nunnaguppala.suryaharsha.cnpokerclub.R;
import com.nunnaguppala.suryaharsha.cnpokerclub.adapters.GroupListAdapter;
import com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.Splitwise;
import com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model.Group;
import com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model.ListGroups;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.GroupEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.viewmodels.GroupsViewModel;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.viewmodels.ViewModelFactory;
import com.nunnaguppala.suryaharsha.cnpokerclub.helpers.Loadable;
import com.nunnaguppala.suryaharsha.cnpokerclub.loaders.AsyncResourceLoader;
import com.nunnaguppala.suryaharsha.cnpokerclub.loaders.SplitwiseGroupListLoader;
import com.wuman.android.auth.oauth2.store.SharedPreferencesCredentialStore;

import java.util.List;
import java.util.concurrent.Executors;

import javax.inject.Inject;

/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * {@link DefaultGroupSelectionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DefaultGroupSelectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DefaultGroupSelectionFragment extends Fragment implements LifecycleOwner{

    GroupListAdapter groupListAdapter;

    @Inject
    ViewModelFactory mViewModelFactory;
    GroupsViewModel groupsViewModel;

    @Inject
    SharedPreferencesCredentialStore sharedPreferencesCredentialStore;

    Spinner spinner;
    Button button;

    private OnFragmentInteractionListener mListener;
    private LifecycleRegistry mLifecycleRegistry;

    public DefaultGroupSelectionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DefaultGroupSelectionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DefaultGroupSelectionFragment newInstance(String param1, String param2) {
        DefaultGroupSelectionFragment fragment = new DefaultGroupSelectionFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        groupListAdapter = new GroupListAdapter(getActivity().getApplicationContext());
        spinner.setAdapter(groupListAdapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((PokerClubApplication)(getActivity().getApplication())).getPokerClubComponent().inject(this);
        mLifecycleRegistry = new LifecycleRegistry(this);
        mLifecycleRegistry.markState(Lifecycle.State.CREATED);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_default_group_selection, container, false);
        spinner = (Spinner) view.findViewById(R.id.group_selection_spinner);
        button = (Button) view.findViewById(R.id.group_selection_button);
        spinner.setEmptyView(view.findViewById(R.id.group_spinner_loading));

        groupsViewModel = ViewModelProviders.of(this, mViewModelFactory).get(GroupsViewModel.class);
        groupsViewModel.init();
        groupsViewModel.getAllGroups().observe((LifecycleOwner) this, new Observer<List<GroupEntity>>() {
            @Override
            public void onChanged(@Nullable List<GroupEntity> groupEntities) {
                groupListAdapter.setData(groupEntities, true);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences sharedPreferences = getActivity()
                                .getSharedPreferences(PokerClubConstants.APPLICATION_PREF_FILE,
                                        Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt(PokerClubConstants.DEFAULT_GROUP_ID_PREF_KEY,
                                ((GroupEntity)spinner.getSelectedItem()).getId());
                        editor.apply();
                    }
                });

            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mLifecycleRegistry.markState(Lifecycle.State.STARTED);
    }

    @Override
    public void onDestroy() {
        mLifecycleRegistry.markState(Lifecycle.State.DESTROYED);
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        mLifecycleRegistry.markState(Lifecycle.State.RESUMED);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;
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
    public interface OnFragmentInteractionListener {
        void onDefaultGroupSelectionFragmentInteraction(int groupId);
    }

}
