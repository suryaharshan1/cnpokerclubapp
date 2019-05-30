package com.nunnaguppala.suryaharsha.cnpokerclub.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.nunnaguppala.suryaharsha.cnpokerclub.R;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.GameBuyInEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.pojos.UserTotalBuyIn;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.viewmodels.GameViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class GameUsersAdapter extends RecyclerView.Adapter<GameUsersAdapter.ViewHolder> {

    private List<UserTotalBuyIn> userBuyIn = Collections.emptyList();

    public void setUserData(List<UserTotalBuyIn> usersData) {
        this.userBuyIn = usersData;
    }

    private Context context;
    private GameViewModel gameViewModel;

    private SparseBooleanArray buyInUIMap;

    public GameUsersAdapter(Context context, GameViewModel gameViewModel) {
        this.context = context;
        this.gameViewModel = gameViewModel;
        this.buyInUIMap = new SparseBooleanArray();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_row, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserTotalBuyIn userTotalBuyIn = userBuyIn.get(position);
        holder.userName.setText(String.valueOf(userTotalBuyIn.getUser().getFirstName()));
        holder.userBuyInInfo.setText(String.valueOf(userTotalBuyIn.getTotalBuyIn()));
        for(GameBuyInEntity gameBuyInEntity : userTotalBuyIn.getGameBuyInEntities()){
            if(!buyInUIMap.get(gameBuyInEntity.getBuyInID(), false) && gameBuyInEntity.getBuyIn() != 0){
                String buyInItem = gameBuyInEntity.getBuyInTime() + " BuyIn: " + gameBuyInEntity.getBuyIn();
                Log.d(GameUsersAdapter.class.getSimpleName(), buyInItem);
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v = layoutInflater.inflate(R.layout.user_buyin_item, null);
                TextView textView = (TextView) v.findViewById(R.id.user_buyin_item_data);
                textView.setText(buyInItem);
                holder.userBuyInContainer.addView(v);
                buyInUIMap.put(gameBuyInEntity.getBuyInID(), true);
            }
        }
        holder.userBuyIn.setTag(userTotalBuyIn);
        holder.userCashOut.setTag(userTotalBuyIn);
        holder.userBuyIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = new EditText(context);
                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setTitle(userTotalBuyIn.getUser().getFirstName() + " BuyIn")
                        .setMessage("BuyIn Amount")
                        .setView(editText)
                        .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                gameViewModel.addBuyInForUserInGame(userTotalBuyIn.getGame().getId(),
                                        userTotalBuyIn.getUser().getId(), Integer.parseInt(editText.getText().toString()));
                            }
                        })
                        .setNegativeButton("Cancel", null).create();
                dialog.show();
            }
        });
        holder.userCashOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = new EditText(context);
                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setTitle(userTotalBuyIn.getUser().getFirstName() + " CashOut")
                        .setMessage("CashOut Amount")
                        .setView(editText)
                        .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                gameViewModel.addCashOutForUserInGame(userTotalBuyIn.getGame().getId(),
                                        userTotalBuyIn.getUser().getId(), Integer.parseInt(editText.getText().toString()));
                            }
                        })
                        .setNegativeButton("Cancel", null).create();
                dialog.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return userBuyIn.size();
    }

    @Override
    public long getItemId(int position) {
        return userBuyIn.get(position).getUser().getId();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView userName, userBuyInInfo;
        public LinearLayout userBuyInContainer;
        public Button userBuyIn, userCashOut;

        public ViewHolder(View itemView) {
            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.game_user_card_name);
            userBuyInInfo = (TextView) itemView.findViewById(R.id.game_user_card_buy_in);
            userBuyInContainer = (LinearLayout) itemView.findViewById(R.id.user_buyin_info_container);
            userBuyIn = (Button) itemView.findViewById(R.id.user_new_buyin);
            userCashOut = (Button) itemView.findViewById(R.id.user_cash_out);
        }
    }
}
