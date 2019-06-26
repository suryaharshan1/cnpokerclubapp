package com.nunnaguppala.suryaharsha.cnpokerclub;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    private long groupId;

    private RecyclerView recyclerView;
    private GameUsersAdapter gameUsersAdapter;
    private CashierAdapter cashierAdapter;
    private Button syncSplitwise;
    private List<UserTotalBuyIn> userBuyInInfo;

    private TextView cashierTitle, cashierCut, totalBuyInInfo, totalCashOutInfo;

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
        setHasOptionsMenu(true);
        mLifecycleRegistry = new LifecycleRegistry(this);
        mLifecycleRegistry.markState(Lifecycle.State.CREATED);
        gameViewModel = ViewModelProviders.of(this, mViewModelFactory).get(GameViewModel.class);
        gameId = getArguments().getInt(ARG_ITEM_ID, -1);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PokerClubConstants.APPLICATION_PREF_FILE, Context.MODE_PRIVATE);
        groupId = sharedPreferences.getInt(PokerClubConstants.DEFAULT_GROUP_ID_PREF_KEY, -1);
        if (gameId == -1) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM dd, HH:mm");
            String gameName = simpleDateFormat.format(Calendar.getInstance().getTime());
            simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy,HH:mm");
            String currentTime = simpleDateFormat.format(Calendar.getInstance().getTime());
            gameEntity = new GameEntity("Game " + gameName, currentTime, currentTime,
                    null, 0, GameEntity.Status.ACTIVE);
            gameId = gameViewModel.createNewGame(gameEntity);
        }
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
                if (cashierCut != null) {
                    SpannableString content = new SpannableString("Cashier Cut: " + gameEntity.getCashierCut());
                    content.setSpan(new UnderlineSpan(), "Cashier Cut: ".length(), content.length(), 0);
                    cashierCut.setText(content);
                }
                if(totalBuyInInfo != null){
                    totalBuyInInfo.setText("Total Game BuyIn: " + String.valueOf(game.getTotalBuyIn()));
                }
                if(totalCashOutInfo != null) {
                    totalCashOutInfo.setText("Total Game CashOut: " + String.valueOf(game.getTotalCashOut()));
                }
                if(game.getTotalCashOut() - game.getTotalBuyIn() == 0 && game.getTotalBuyIn() != 0 &&
                game.getStatus() != GameEntity.Status.PUBLISHED) {
                    syncSplitwise.setVisibility(View.VISIBLE);
                } else {
                    syncSplitwise.setVisibility(View.GONE);
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
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, 0) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                GameUsersAdapter adapter = (GameUsersAdapter) recyclerView.getAdapter();
                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();
                adapter.moveItem(from, to);
                adapter.notifyItemMoved(from, to);
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                if(actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                    viewHolder.itemView.setAlpha(0.5f);
                }
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                viewHolder.itemView.setAlpha(1.0f);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        gameViewModel.getUsersBuyInForGame(gameId).observe(this, new Observer<List<UserTotalBuyIn>>() {
            @Override
            public void onChanged(@Nullable List<UserTotalBuyIn> userTotalBuyIns) {
                userBuyInInfo = userTotalBuyIns;
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
        cashierCut = (TextView) rootView.findViewById(R.id.cashier_cut);
        cashierCut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = new EditText(getContext());
                AlertDialog dialog = new AlertDialog.Builder(getContext())
                        .setTitle("Cashier Cut")
                        .setView(editText)
                        .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                gameViewModel.setCashierCutForGame(gameEntity,
                                        Integer.parseInt(editText.getText().toString()));
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
            }
        });
        totalBuyInInfo = (TextView) rootView.findViewById(R.id.game_total_buyIn);
        totalCashOutInfo = (TextView) rootView.findViewById(R.id.game_total_cashOut);
        syncSplitwise = (Button) rootView.findViewById(R.id.sync_splitwise);
        syncSplitwise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameViewModel.syncGameWithSplitwise(gameEntity, userBuyInInfo, groupId);
            }
        });
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_game, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.game_share:
                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                StringBuilder s = new StringBuilder(gameEntity.getName());
                s.append("\n");
                s.append("Cashier:\t");
                s.append(gameEntity.getCashier().getFirstName());
                s.append("\t");
                s.append(gameEntity.getCashier().getLastName());
                s.append("\t");
                s.append("Cashier Cut:\t");
                s.append(gameEntity.getCashierCut());
                s.append("\teach");
                s.append("\n\n");
                for(UserTotalBuyIn userBuyIn : userBuyInInfo) {
                    s.append(userBuyIn.getUser().getFirstName());
                    s.append(userBuyIn.getUser().getLastName());
                    s.append("\t");
                    s.append(userBuyIn.getTotalBuyIn());
                    s.append("\t");
                    s.append(userBuyIn.getTotalCashOut());
                    s.append("\n");
                }
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, s.toString());
                try {
                    getActivity().startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getContext(), "Whatsapp application not found", Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.game_log_hands:
                Toast.makeText(getContext(), "Feature in development", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
