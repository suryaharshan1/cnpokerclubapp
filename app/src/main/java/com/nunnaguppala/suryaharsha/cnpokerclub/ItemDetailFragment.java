package com.nunnaguppala.suryaharsha.cnpokerclub;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.GameEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.viewmodels.GameViewModel;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.viewmodels.ViewModelFactory;
import com.nunnaguppala.suryaharsha.cnpokerclub.dummy.DummyContent;

import java.util.List;

import javax.inject.Inject;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment implements LifecycleOwner  {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;
    private GameEntity gameEntity;
    private long gameId;

    @Inject
    ViewModelFactory mViewModelFactory;
    GameViewModel gameViewModel;

    private LifecycleRegistry mLifecycleRegistry;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((PokerClubApplication)getActivity().getApplication()).
                getPokerClubComponent().inject(this);
        mLifecycleRegistry = new LifecycleRegistry(this);
        mLifecycleRegistry.markState(Lifecycle.State.CREATED);
        gameViewModel = ViewModelProviders.of(this, mViewModelFactory).get(GameViewModel.class);
        gameId = getArguments().getInt(ARG_ITEM_ID, -1);
        if (gameId == -1) {
            gameEntity = new GameEntity("Game 01", "Now", "Now",
                    null, 0, GameEntity.Status.ACTIVE);
            gameId = gameViewModel.createNewGame(gameEntity);
        }
        gameViewModel.getGameInfo((int)gameId).observe(this, new Observer<List<GameEntity>>() {
            @Override
            public void onChanged(@Nullable List<GameEntity> gameEntities) {
                if(gameEntities == null || gameEntities.isEmpty()){
                    gameEntity = null;
                }
                else {
                    gameEntity = gameEntities.get(0);
                }
                Log.d(ItemDetailFragment.class.getSimpleName(), String.valueOf(gameId));
                Log.d(ItemDetailFragment.class.getSimpleName(), String.valueOf(gameEntity.getId()));
                Activity activity = getActivity();
                CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
                if (appBarLayout != null) {
                    appBarLayout.setTitle(gameEntity.getName());

                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.item_detail)).setText(mItem.details);
        }

        return rootView;
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;
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
}
