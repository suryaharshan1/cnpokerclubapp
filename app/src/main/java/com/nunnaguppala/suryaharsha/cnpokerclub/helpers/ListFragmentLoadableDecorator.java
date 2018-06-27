package com.nunnaguppala.suryaharsha.cnpokerclub.helpers;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.SparseBooleanArray;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.api.client.util.Preconditions;
import com.nunnaguppala.suryaharsha.cnpokerclub.R;
import com.nunnaguppala.suryaharsha.cnpokerclub.loaders.AsyncResourceLoader;

public class ListFragmentLoadableDecorator<T> implements LoaderManager.LoaderCallbacks<AsyncResourceLoader.Result<T>> {

    private final LoaderManager.LoaderCallbacks<AsyncResourceLoader.Result<T>> mCallbacks;
    private final int mLoaderId;
    private final ListFragment mListFragment;
    private final AdapterView<?> mAdapterView;
    private final SparseBooleanArray mActive = new SparseBooleanArray();

    public ListFragmentLoadableDecorator(android.support.v4.app.LoaderManager.LoaderCallbacks<AsyncResourceLoader.Result<T>> callbacks, int loaderId,
                                         ListFragment listFragment) {
        super();
        this.mCallbacks = Preconditions.checkNotNull(callbacks);
        this.mLoaderId = loaderId;
        this.mListFragment = Preconditions.checkNotNull(listFragment);
        this.mAdapterView = Preconditions.checkNotNull(listFragment.getListView());

        mListFragment.setListShown(false);
    }

    @Override
    public Loader<AsyncResourceLoader.Result<T>> onCreateLoader(int id, Bundle args) {
        mActive.put(id, true);
        if (mLoaderId == id) {
            mListFragment.setListShown(!isEmpty());
        }
        updateWindowIndeterminateProgress();
        return mCallbacks.onCreateLoader(id, args);
    }

    @Override
    public void onLoadFinished(Loader<AsyncResourceLoader.Result<T>> loader, AsyncResourceLoader.Result<T> result) {
        mCallbacks.onLoadFinished(loader, result);
        mActive.delete(loader.getId());
        if (mLoaderId == loader.getId()) {
            if (mListFragment.isResumed()) {
                mListFragment.setListShown(true);
            } else {
                mListFragment.setListShownNoAnimation(true);
            }
            if (!result.success) {
                Toast.makeText(mListFragment.getActivity(),
                        result.errorMessage, Toast.LENGTH_LONG).show();
            }
            mListFragment.setEmptyText(mListFragment.getString(R.string.empty));
        }
        updateWindowIndeterminateProgress();
    }

    @Override
    public void onLoaderReset(Loader<AsyncResourceLoader.Result<T>> loader) {
        mCallbacks.onLoaderReset(loader);
        mActive.delete(loader.getId());
        if (mLoaderId == loader.getId()) {
            if (mListFragment.isVisible()) {
                mListFragment.setListShown(true);
            }
        }
        updateWindowIndeterminateProgress();
    }

    private void updateWindowIndeterminateProgress() {
        if (mListFragment.isVisible()) {
            mListFragment.getActivity().setProgressBarIndeterminateVisibility(mActive.size() != 0);
        }
    }

    private boolean isEmpty() {
        Adapter adapter = mAdapterView.getAdapter();
        return adapter == null || adapter.isEmpty();
    }
}
