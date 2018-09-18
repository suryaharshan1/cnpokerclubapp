package com.nunnaguppala.suryaharsha.cnpokerclub.adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.nunnaguppala.suryaharsha.cnpokerclub.R;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.ExpenseUserShareEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.UserEntity;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.pojos.ExpenseUserShareAndDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.stream.IntStream;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {
    private List<ExpenseUserShareAndDetails> usersShareAndDetails = Collections.<ExpenseUserShareAndDetails>emptyList();
    private Executor executor;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView userName, totalGames, totalDebt;
        public GraphView winningsGraph;

        public ViewHolder(View v) {
            super(v);
            userName = (TextView) v.findViewById(R.id.user_name);
            totalGames = (TextView) v.findViewById(R.id.user_total_games);
            totalDebt = (TextView) v.findViewById(R.id.user_total_debt);
            winningsGraph = (GraphView) v.findViewById(R.id.user_winnings_graph);
        }
    }

    public UsersAdapter setUsersAndShares(List<ExpenseUserShareAndDetails> usersAndShares, Executor executor) {
        this.usersShareAndDetails = usersAndShares;
        this.executor = executor;
        return this;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row_layout, parent, false);
        return new ViewHolder(view);
    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ExpenseUserShareAndDetails userAndDetails = usersShareAndDetails.get(position);
        holder.userName.setText(userAndDetails.getUser().getFirstName() + " " + userAndDetails.getUser().getLastName());
        double totalDebtOfUser = 0.0;
        final LineGraphSeries<DataPoint> lineGraphSeries = new LineGraphSeries<DataPoint>();
        final LineGraphSeries<DataPoint> cummGraphSeries = new LineGraphSeries<DataPoint>();
        cummGraphSeries.setColor(Color.GREEN);
        cummGraphSeries.setTitle("Cumulative Winnings");
        lineGraphSeries.setTitle("Game Winnings");
        lineGraphSeries.setColor(Color.BLUE);
        int counter = 0;
        IntStream.range(0, userAndDetails.getShares().size()).forEach(
                index -> {
                    lineGraphSeries.appendData(new DataPoint(index, Double.valueOf(userAndDetails.getShares().get(index).getNetBalance())),
                            false, 500);
                }
        );
        for(ExpenseUserShareEntity share : userAndDetails.getShares()){
            totalDebtOfUser += Double.valueOf(share.getNetBalance());
//            lineGraphSeries.appendData(new DataPoint(counter, Double.valueOf(share.getNetBalance())),
//                    false, 500);
            cummGraphSeries.appendData(new DataPoint(counter, totalDebtOfUser), false, 500);
            counter++;
        }
        holder.totalDebt.setText(String.valueOf(totalDebtOfUser));
        holder.totalGames.setText(String.valueOf(userAndDetails.getShares().size()));
        holder.winningsGraph.addSeries(lineGraphSeries);
        holder.winningsGraph.addSeries(cummGraphSeries);
//        holder.winningsGraph.getLegendRenderer().setVisible(true);
    }

    @Override
    public int getItemCount() {
        return usersShareAndDetails.size();
    }

    @Override
    public long getItemId(int position) {
        return usersShareAndDetails.get(position).getUser().getId();
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        holder.winningsGraph.removeAllSeries();
        super.onViewRecycled(holder);
    }
}
