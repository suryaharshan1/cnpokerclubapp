package com.nunnaguppala.suryaharsha.cnpokerclub;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.nunnaguppala.suryaharsha.cnpokerclub.adapters.CashierAdapter;
import com.nunnaguppala.suryaharsha.cnpokerclub.adapters.GameUsersAdapter;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.GameEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.UserEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.pojos.UserTotalBuyIn;
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

    private RecyclerView recyclerView;
    private GameUsersAdapter gameUsersAdapter;
    private CashierAdapter cashierAdapter;

    private Spinner spinner;
    private TextView cashierTitle;

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

    @RequiresApi(api = Build.VERSION_CODES.N)
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
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM dd, HH:mm");
            String gameName = simpleDateFormat.format(Calendar.getInstance().getTime());
            simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy,HH:mm");
            String currentTime = simpleDateFormat.format(Calendar.getInstance().getTime());
            gameEntity = new GameEntity("Game " + gameName, currentTime, currentTime,
                    null, 0, GameEntity.Status.ACTIVE);
            gameId = gameViewModel.createNewGame(gameEntity);
        }
//        gameViewModel.getGameWithCashierInfo((int)gameId).observe(this, new Observer<GameInfo>() {
//            @Override
//            public void onChanged(@Nullable GameInfo gameInfo) {
//                if(gameInfo == null){
//                    return;
//                }
//                gameEntity = gameInfo.getGame();
//                Log.d(ItemDetailFragment.class.getSimpleName(), String.valueOf(gameId));
//                Log.d(ItemDetailFragment.class.getSimpleName(), String.valueOf(gameEntity.getId()));
//                Activity activity = getActivity();
//                CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
//                if (appBarLayout != null) {
//                    appBarLayout.setTitle(gameEntity.getName());
//                }
//
//                if (cashierTitle != null) {
//                    Log.d(ItemDetailFragment.class.getSimpleName(), "CashierTitle is Not Null");
//                    UserEntity userEntity = gameInfo.getCashier();
//                    if(userEntity == null){
//                        Log.d(ItemDetailFragment.class.getSimpleName(), "UserEntity is Null");
//                        SpannableString content = new SpannableString("Select Cashier");
//                        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
//                        cashierTitle.setText(content);
//                    } else {
//                        SpannableString content = new SpannableString("Cashier: " + userEntity.getFirstName());
//                        content.setSpan(new UnderlineSpan(), 0,
//                                content.length() - userEntity.getFirstName().length(), 0);
//                        cashierTitle.setText(content);
//                    }
//                } else {
//                    Log.d(ItemDetailFragment.class.getSimpleName(), "CashierTitle is Null");
//                }
//            }
//        });
        gameViewModel.getGameInfo((int)gameId).observe(this, new Observer<GameEntity>() {
            @Override
            public void onChanged(@Nullable GameEntity game) {
                if(game == null){
                    return;
                }
                gameEntity = game;
                Log.d(ItemDetailFragment.class.getSimpleName(), String.valueOf(gameId));
                Log.d(ItemDetailFragment.class.getSimpleName(), String.valueOf(gameEntity.getId()));
                Activity activity = getActivity();
                CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
                if (appBarLayout != null) {
                    appBarLayout.setTitle(gameEntity.getName());
                }
                if (cashierTitle != null) {
                    Log.d(ItemDetailFragment.class.getSimpleName(), "CashierTitle is Not Null");
                    UserEntity userEntity = game.getCashier();
                    if(userEntity == null){
                        Log.d(ItemDetailFragment.class.getSimpleName(), "UserEntity is Null");
                        SpannableString content = new SpannableString("Select Cashier");
                        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                        cashierTitle.setText(content);
                    } else {
                        SpannableString content = new SpannableString("Cashier: " + userEntity.getFirstName());
                        content.setSpan(new UnderlineSpan(), content.length() - userEntity.getFirstName().length(),
                                content.length(), 0);
                        cashierTitle.setText(content);
                    }
                } else {
                    Log.d(ItemDetailFragment.class.getSimpleName(), "CashierTitle is Null");
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);
        recyclerView = rootView.findViewById(R.id.game_users_list);
        gameUsersAdapter = new GameUsersAdapter(getContext(), gameViewModel);
        gameUsersAdapter.setHasStableIds(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(gameUsersAdapter);
        recyclerView.setItemViewCacheSize(40);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        gameViewModel.getUsersBuyInForGame(gameId).observe(this, new Observer<List<UserTotalBuyIn>>() {
            @Override
            public void onChanged(@Nullable List<UserTotalBuyIn> userTotalBuyIns) {
                gameUsersAdapter.setUserData(userTotalBuyIns);
                gameUsersAdapter.notifyDataSetChanged();
            }
        });

        cashierAdapter = new CashierAdapter(getContext());

        gameViewModel.getPlayerListForGame(gameId).observe(this, new Observer<List<UserEntity>>() {
            @Override
            public void onChanged(@Nullable List<UserEntity> userEntities) {
                cashierAdapter.setData(userEntities, true);
                cashierAdapter.notifyDataSetChanged();
            }
        });
        cashierTitle = (TextView) rootView.findViewById(R.id.cashier_title);
//        gameViewModel.getCashierForGame(gameId).observe(this, new Observer<UserEntity>() {
//            @Override
//            public void onChanged(@Nullable UserEntity userEntity) {
//
//            }
//        });
        cashierTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                dialogBuilder.setTitle("Select Cashier");
                dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialogBuilder.setAdapter(cashierAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                          UserEntity user = cashierAdapter.getItem(which);
                          gameViewModel.setCashierForGame(gameEntity, user.getId());
                    }
                });
                dialogBuilder.show();
            }
        });
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
