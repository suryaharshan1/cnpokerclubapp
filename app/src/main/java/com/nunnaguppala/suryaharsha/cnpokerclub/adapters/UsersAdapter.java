package com.nunnaguppala.suryaharsha.cnpokerclub.adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
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
    private SparseArray<UserDataHolder> cacheUserData = new SparseArray<>();

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
        if (cacheUserData.get(userAndDetails.getUser().getId()) == null) {
            double totalDebtOfUser = 0.0;
            int counter = 1;
            DataPoint[] gameDataPoints;
            DataPoint[] cummDataPoints;
            if(userAndDetails.getShares().size() > 0) {
                gameDataPoints = new DataPoint[userAndDetails.getShares().size()+1];
                cummDataPoints = new DataPoint[userAndDetails.getShares().size()+1];
                gameDataPoints[0] = new DataPoint(0, 0);
                cummDataPoints[0] = new DataPoint(0,0);
                for(ExpenseUserShareEntity share : userAndDetails.getShares()) {
                    totalDebtOfUser += Double.valueOf(share.getNetBalance());
                    gameDataPoints[counter] = new DataPoint(counter, Double.valueOf(share.getNetBalance()));
                    cummDataPoints[counter] = new DataPoint(counter, totalDebtOfUser);
                    counter++;
                }
            } else {
                gameDataPoints = new DataPoint[] {new DataPoint(0, 0)};
                cummDataPoints = new DataPoint[] {new DataPoint(0, 0)};
            }
            final LineGraphSeries<DataPoint> lineGraphSeries = new LineGraphSeries<DataPoint>(gameDataPoints);
            final LineGraphSeries<DataPoint> cummGraphSeries = new LineGraphSeries<DataPoint>(cummDataPoints);
            cummGraphSeries.setColor(Color.GREEN);
            cummGraphSeries.setTitle("Cumulative Winnings");
            lineGraphSeries.setTitle("Game Winnings");
            lineGraphSeries.setColor(Color.BLUE);
            cacheUserData.put(userAndDetails.getUser().getId(), new UserDataHolder(lineGraphSeries, cummGraphSeries, totalDebtOfUser, userAndDetails.getShares().size()));
        }
        holder.userName.setText(userAndDetails.getUser().getFirstName() + " " + userAndDetails.getUser().getLastName());
        holder.totalDebt.setText(String.valueOf(cacheUserData.get(userAndDetails.getUser().getId()).getTotalDebt()));
        holder.totalGames.setText(String.valueOf(userAndDetails.getShares().size()));
        holder.winningsGraph.getViewport().setXAxisBoundsManual(true);
        holder.winningsGraph.getViewport().setMinX(0.0);
        holder.winningsGraph.getViewport().setMaxX(userAndDetails.getShares().size()+1);
        holder.winningsGraph.addSeries(cacheUserData.get(userAndDetails.getUser().getId()).getLineGraphSeries());
        holder.winningsGraph.addSeries(cacheUserData.get(userAndDetails.getUser().getId()).getCummGraphSeries());
//        holder.winningsGraph.getLegendRenderer().setVisible(true);
    }

    @Override
    public int getItemCount() {
        return usersShareAndDetails.size();
    }

    @Override
    public long getItemId(int position) {
        return usersShareAndDetails.get(position).hashCode();
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        holder.winningsGraph.removeAllSeries();
        super.onViewRecycled(holder);
    }

    private class UserDataHolder {
        private LineGraphSeries<DataPoint> lineGraphSeries;
        private LineGraphSeries<DataPoint> cummGraphSeries;
        private double totalDebt;
        private int size;

        public UserDataHolder(LineGraphSeries<DataPoint> lineGraphSeries, LineGraphSeries<DataPoint> cummGraphSeries, double totalDebt, int size) {
            this.lineGraphSeries = lineGraphSeries;
            this.cummGraphSeries = cummGraphSeries;
            this.totalDebt = totalDebt;
            this.size = size;
        }

        public LineGraphSeries<DataPoint> getLineGraphSeries() {
            return lineGraphSeries;
        }

        public void setLineGraphSeries(LineGraphSeries<DataPoint> lineGraphSeries) {
            this.lineGraphSeries = lineGraphSeries;
        }

        public LineGraphSeries<DataPoint> getCummGraphSeries() {
            return cummGraphSeries;
        }

        public void setCummGraphSeries(LineGraphSeries<DataPoint> cummGraphSeries) {
            this.cummGraphSeries = cummGraphSeries;
        }

        public double getTotalDebt() {
            return totalDebt;
        }

        public void setTotalDebt(double totalDebt) {
            this.totalDebt = totalDebt;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }
}
