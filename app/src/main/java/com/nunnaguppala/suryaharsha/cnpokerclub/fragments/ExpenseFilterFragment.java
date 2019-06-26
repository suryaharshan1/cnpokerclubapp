package com.nunnaguppala.suryaharsha.cnpokerclub.fragments;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nunnaguppala.suryaharsha.cnpokerclub.PokerClubApplication;
import com.nunnaguppala.suryaharsha.cnpokerclub.R;
import com.nunnaguppala.suryaharsha.cnpokerclub.adapters.ExpenseFilterAdapter;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.ExpenseEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.viewmodels.ExpensesViewModel;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.viewmodels.ViewModelFactory;
import com.nunnaguppala.suryaharsha.cnpokerclub.fragments.dummy.DummyContent;
import com.nunnaguppala.suryaharsha.cnpokerclub.fragments.dummy.DummyContent.DummyItem;

import java.util.List;

import javax.inject.Inject;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ExpenseFilterFragment extends Fragment implements LifecycleOwner  {

    // TODO: Customize parameter argument names
    private static final String ARG_GROUP_ID = "groupId";
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private int groupId = -1;
    private OnListFragmentInteractionListener mListener;

    private LifecycleRegistry mLifecycleRegistry;
    ExpensesViewModel expensesViewModel;
    private ExpenseFilterAdapter expenseFilterAdapter;

    @Inject
    ViewModelFactory mViewModelFactory;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ExpenseFilterFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ExpenseFilterFragment newInstance(int groupId) {
        ExpenseFilterFragment fragment = new ExpenseFilterFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_GROUP_ID, groupId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((PokerClubApplication)(getActivity().getApplication())).getPokerClubComponent().inject(this);
        mLifecycleRegistry = new LifecycleRegistry(this);
        mLifecycleRegistry.markState(Lifecycle.State.CREATED);
        if (getArguments() != null) {
            groupId = getArguments().getInt(ARG_GROUP_ID);
        }
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense_filter_list, container, false);
        expensesViewModel = ViewModelProviders.of(this, mViewModelFactory).get(ExpensesViewModel.class);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            expenseFilterAdapter = new ExpenseFilterAdapter();
            recyclerView.setAdapter(expenseFilterAdapter);
            ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                    switch (direction) {
                        case ItemTouchHelper.RIGHT:
                            expensesViewModel.addExpenseFilter(((ExpenseFilterAdapter.ViewHolder)viewHolder).mItem.getId(), true);
                            expenseFilterAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                            break;
                        case ItemTouchHelper.LEFT:
                            expensesViewModel.addExpenseFilter(((ExpenseFilterAdapter.ViewHolder)viewHolder).mItem.getId(), false);
                            expenseFilterAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                            break;
                        default:
                            Toast.makeText(getContext(), "Cannot recognise action", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            };
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
            itemTouchHelper.attachToRecyclerView(recyclerView);
            expensesViewModel.getUncategorisedExpensesInGroup(groupId).observe(this, new Observer<List<ExpenseEntity>>() {
                @Override
                public void onChanged(@Nullable List<ExpenseEntity> expenseEntities) {
                    expenseFilterAdapter.setExpenses(expenseEntities);
                    expenseFilterAdapter.notifyDataSetChanged();
                }
            });
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(ExpenseEntity item);
    }
}
