package com.nunnaguppala.suryaharsha.cnpokerclub.helpers;

import android.support.v4.app.LoaderManager;

import com.nunnaguppala.suryaharsha.cnpokerclub.loaders.AsyncResourceLoader;

public interface Loadable<T> extends LoaderManager.LoaderCallbacks<AsyncResourceLoader.Result<T>> {

    boolean hasMore();

    boolean hasError();

    void init();

    void destroy();

    boolean isReadyToLoadMore();

    void loadMore();
}
