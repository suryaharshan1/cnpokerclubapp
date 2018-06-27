package com.nunnaguppala.suryaharsha.cnpokerclub.helpers;

import android.widget.AbsListView;

import com.google.api.client.util.Preconditions;

public class ListScrollListener implements AbsListView.OnScrollListener {

    private final Loadable<?> mLoadable;

    public ListScrollListener(Loadable<?> loadable) {
        super();
        this.mLoadable = Preconditions.checkNotNull(loadable);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                         int totalItemCount) {
        if (firstVisibleItem + visibleItemCount >= totalItemCount) {
            if (mLoadable.isReadyToLoadMore()) {
                mLoadable.loadMore();
            }
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // TODO Auto-generated method stub

    }

}
