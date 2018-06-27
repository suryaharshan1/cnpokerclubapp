package com.nunnaguppala.suryaharsha.cnpokerclub.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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

import com.nunnaguppala.suryaharsha.cnpokerclub.R;
import com.nunnaguppala.suryaharsha.cnpokerclub.adapters.GroupListAdapter;
import com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model.Group;
import com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model.ListGroups;
import com.nunnaguppala.suryaharsha.cnpokerclub.helpers.Loadable;
import com.nunnaguppala.suryaharsha.cnpokerclub.loaders.AsyncResourceLoader;
import com.nunnaguppala.suryaharsha.cnpokerclub.loaders.SplitwiseGroupListLoader;

/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * {@link DefaultGroupSelectionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DefaultGroupSelectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DefaultGroupSelectionFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<AsyncResourceLoader.Result<ListGroups>>{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    GroupListAdapter groupListAdapter;
    Loadable<ListGroups> mLoadable;

    Spinner spinner;
    Button button;

    private OnFragmentInteractionListener mListener;

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
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        groupListAdapter = new GroupListAdapter(getActivity().getApplicationContext());
//        groupListAdapter.setDropDownViewResource(R.layout.group_selector_list_item);
        spinner.setAdapter(groupListAdapter);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_default_group_selection, container, false);
        spinner = (Spinner) view.findViewById(R.id.group_selection_spinner);
        button = (Button) view.findViewById(R.id.group_selection_button);
        spinner.setEmptyView(view.findViewById(R.id.group_spinner_loading));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Group group = (Group) spinner.getSelectedItem();
                Toast.makeText(getActivity(), group.getName(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    @Override
    public void onDestroy() {
        mLoadable.destroy();
        super.onDestroy();
    }

    @Override
    public Loader<AsyncResourceLoader.Result<ListGroups>> onCreateLoader(int id, Bundle args) {
        return new SplitwiseGroupListLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<AsyncResourceLoader.Result<ListGroups>> loader, AsyncResourceLoader.Result<ListGroups> data) {
        groupListAdapter.setData(data.data, true);
    }

    @Override
    public void onLoaderReset(Loader<AsyncResourceLoader.Result<ListGroups>> loader) {
        groupListAdapter.setData(null, true);
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
