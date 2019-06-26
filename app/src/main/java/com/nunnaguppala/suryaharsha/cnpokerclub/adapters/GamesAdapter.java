package com.nunnaguppala.suryaharsha.cnpokerclub.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nunnaguppala.suryaharsha.cnpokerclub.ItemDetailActivity;
import com.nunnaguppala.suryaharsha.cnpokerclub.ItemDetailFragment;
import com.nunnaguppala.suryaharsha.cnpokerclub.ItemListActivity;
import com.nunnaguppala.suryaharsha.cnpokerclub.R;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.GameEntity;

import java.util.Collections;
import java.util.List;

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.ViewHolder> {
    private List<GameEntity> games = Collections.emptyList();
    private final ItemListActivity mParentActivity;
    private final boolean mTwoPane;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            GameEntity gameEntity = (GameEntity) view.getTag();
            if (mTwoPane) {
                Bundle arguments = new Bundle();
                arguments.putInt(ItemDetailFragment.ARG_ITEM_ID, gameEntity.getId());
                ItemDetailFragment fragment = new ItemDetailFragment();
                fragment.setArguments(arguments);
                mParentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.item_detail_container, fragment)
                        .commit();
            } else {
                Context context = view.getContext();
                Intent intent = new Intent(context, ItemDetailActivity.class);
                intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, gameEntity.getId());

                context.startActivity(intent);
            }
        }
    };

    public GamesAdapter(ItemListActivity mParentActivity, List<GameEntity> games,
                        boolean mTwoPane) {
        this.games = games;
        this.mParentActivity = mParentActivity;
        this.mTwoPane = mTwoPane;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView gameName;

        public ViewHolder(View itemView) {
            super(itemView);
            gameName = (TextView) itemView.findViewById(R.id.id_text);
        }
    }

    public GamesAdapter setGames(List<GameEntity> games) {
        this.games = games;
        return this;
    }

    @Override
    public GamesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_content, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GamesAdapter.ViewHolder viewHolder, int i) {
        GameEntity game = games.get(i);
        viewHolder.gameName.setText(game.getName());
        viewHolder.itemView.setTag(games.get(i));
        viewHolder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return games.size();
    }
}
