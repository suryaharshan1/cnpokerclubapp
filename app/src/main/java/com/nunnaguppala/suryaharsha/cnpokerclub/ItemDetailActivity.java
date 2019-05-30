package com.nunnaguppala.suryaharsha.cnpokerclub;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.abdeveloper.library.MultiSelectDialog;
import com.abdeveloper.library.MultiSelectModel;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.GameEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.UserEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.viewmodels.GameViewModel;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.viewmodels.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ItemListActivity}.
 */
public class ItemDetailActivity extends AppCompatActivity implements LifecycleOwner {

    @Inject
    ViewModelFactory mViewModelFactory;
    GameViewModel gameViewModel;
    private LifecycleRegistry lifecycleRegistry;
    private long gameId;
    private long groupId;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        ((PokerClubApplication)getApplication()).getPokerClubComponent().inject(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        lifecycleRegistry = new LifecycleRegistry(this);
        lifecycleRegistry.markState(Lifecycle.State.CREATED);

        gameViewModel = ViewModelProviders.of(this, mViewModelFactory).get(GameViewModel.class);

        SharedPreferences sharedPreferences = getSharedPreferences(PokerClubConstants.APPLICATION_PREF_FILE, MODE_PRIVATE);
        groupId = sharedPreferences.getInt(PokerClubConstants.DEFAULT_GROUP_ID_PREF_KEY, -1);
        gameId = getIntent().getIntExtra(ItemDetailFragment.ARG_ITEM_ID, -1);
        if (gameId == -1) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM dd, HH:mm");
            String gameName = simpleDateFormat.format(Calendar.getInstance().getTime());
            simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy,HH:mm");
            String currentTime = simpleDateFormat.format(Calendar.getInstance().getTime());
            GameEntity gameEntity = new GameEntity("Game " + gameName, currentTime, currentTime,
                    null, 0, GameEntity.Status.ACTIVE);
            gameId = gameViewModel.createNewGame(gameEntity);
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        MultiSelectDialog multiSelectDialog = new MultiSelectDialog()
                .title(getResources().getString(R.string.title_add_users))
                .titleSize(25)
                .positiveText("Done")
                .negativeText("Cancel")
                .setMinSelectionLimit(1)
                .setMaxSelectionLimit(11)
                .multiSelectList(new ArrayList<>())
                .onSubmit(new MultiSelectDialog.SubmitCallbackListener() {
                    @Override
                    public void onSelected(ArrayList<Integer> arrayList, ArrayList<String> arrayList1, String s) {
                        gameViewModel.onBoardUsersToGame((int)gameId, arrayList);
                    }

                    @Override
                    public void onCancel() {

                    }
                });

        gameViewModel.getNonPlayerListForGame((int)gameId, (int)groupId).
                observe(this, new Observer<List<UserEntity>>() {
                    @Override
                    public void onChanged(@Nullable List<UserEntity> userEntities) {
                        ArrayList<MultiSelectModel> listOfUsers = new ArrayList<MultiSelectModel>();
                        for(UserEntity userEntity : userEntities){
                            listOfUsers.add(new MultiSelectModel(userEntity.getId(),
                                    userEntity.getFirstName() + " " +
                                            userEntity.getLastName()));
                        }
                        multiSelectDialog.multiSelectList(listOfUsers);
                        multiSelectDialog.preSelectIDsList(new ArrayList<Integer>());
                    }
                });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                multiSelectDialog.show(getSupportFragmentManager(), "multiSelectDialog");
            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putInt(ItemDetailFragment.ARG_ITEM_ID, (int)gameId);
            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, ItemListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return lifecycleRegistry;
    }

    @Override
    protected void onStart() {
        super.onStart();
        lifecycleRegistry.markState(Lifecycle.State.STARTED);
    }

    @Override
    protected void onResume() {
        super.onResume();
        lifecycleRegistry.markState(Lifecycle.State.RESUMED);
    }

    @Override
    protected void onDestroy() {
        lifecycleRegistry.markState(Lifecycle.State.DESTROYED);
        super.onDestroy();
    }
}
