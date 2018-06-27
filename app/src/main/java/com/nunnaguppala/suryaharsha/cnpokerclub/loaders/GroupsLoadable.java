package com.nunnaguppala.suryaharsha.cnpokerclub.loaders;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextUtils;

import com.google.api.client.util.Preconditions;
import com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model.Group;
import com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model.ListGroups;
import com.nunnaguppala.suryaharsha.cnpokerclub.helpers.Loadable;

import java.util.List;

public class GroupsLoadable implements Loadable<ListGroups> {
    private static final String ARG_MAX_ID = "loadable:max-id";

    private final LoaderManager mLoaderManager;
    private final int mLoaderId;
    private final LoaderManager.LoaderCallbacks<AsyncResourceLoader.Result<ListGroups>> mCallbacks;

    private boolean mIsLoadingMore;
    private boolean mHasError;
    private boolean mHasMore;
    private String mNextMaxId;

    public GroupsLoadable(LoaderManager loaderManager, int loaderId,
                          LoaderManager.LoaderCallbacks<AsyncResourceLoader.Result<ListGroups>> callbacks) {
        super();
        this.mLoaderManager = Preconditions.checkNotNull(loaderManager);
        this.mLoaderId = loaderId;
        this.mCallbacks = callbacks;
    }

    @Override
    public void init() {
        mLoaderManager.initLoader(mLoaderId, Bundle.EMPTY, this);
    }

    @Override
    public boolean isReadyToLoadMore() {
//        return mHasMore && !mHasError && !mIsLoadingMore;
          return false;
    }

    @Override
    public void loadMore() {
        mIsLoadingMore = true;
        Bundle args = new Bundle();
        args.putString(ARG_MAX_ID, mNextMaxId);
        mLoaderManager.restartLoader(mLoaderId, args, this);
    }

    @Override
    public void destroy() {
        mLoaderManager.destroyLoader(mLoaderId);
    }

    @Override
    public Loader<AsyncResourceLoader.Result<ListGroups>> onCreateLoader(int id, Bundle args) {
        return mCallbacks.onCreateLoader(id, args);
    }

    @Override
    public void onLoadFinished(Loader<AsyncResourceLoader.Result<ListGroups>> loader, AsyncResourceLoader.Result<ListGroups> result) {
        mHasError = result == null || !result.success;
        if (!mHasError) {
            List<Group> groups = result.data.getGroups();
            if (groups.size() > 0) {
                mNextMaxId = String.valueOf(groups.get(groups.size() - 1).getId());
            } else {
                mNextMaxId = null;
            }
        } else {
            mNextMaxId = null;
        }
//        mHasMore = !TextUtils.isEmpty(mNextMaxId);
        mHasMore = false;
        mIsLoadingMore = false;
        mCallbacks.onLoadFinished(loader, result);
    }

    @Override
    public void onLoaderReset(Loader<AsyncResourceLoader.Result<ListGroups>> loader) {
        mHasError = false;
        mHasMore = false;
        mNextMaxId = null;
        mIsLoadingMore = false;
        mCallbacks.onLoaderReset(loader);
    }

    @Override
    public boolean hasError() {
        return mHasError;
    }

    @Override
    public boolean hasMore() {
        return mHasMore;
    }
}
