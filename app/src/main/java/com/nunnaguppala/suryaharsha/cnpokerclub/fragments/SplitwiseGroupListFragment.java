package com.nunnaguppala.suryaharsha.cnpokerclub.fragments;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.nunnaguppala.suryaharsha.cnpokerclub.adapters.ContentDecoratorAdapter;
import com.nunnaguppala.suryaharsha.cnpokerclub.adapters.GroupListAdapter;
import com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model.Group;
import com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model.ListGroups;
import com.nunnaguppala.suryaharsha.cnpokerclub.helpers.ListScrollListener;
import com.nunnaguppala.suryaharsha.cnpokerclub.helpers.Loadable;
import com.nunnaguppala.suryaharsha.cnpokerclub.helpers.ListFragmentLoadableDecorator;
import com.nunnaguppala.suryaharsha.cnpokerclub.loaders.AsyncResourceLoader;
import com.nunnaguppala.suryaharsha.cnpokerclub.loaders.GroupsLoadable;
import com.nunnaguppala.suryaharsha.cnpokerclub.loaders.SplitwiseGroupListLoader;

public class SplitwiseGroupListFragment extends ListFragment
        implements LoaderManager.LoaderCallbacks<AsyncResourceLoader.Result<ListGroups>>{

    GroupListAdapter groupListAdapter;
    Loadable<ListGroups> mLoadable;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

        groupListAdapter = new GroupListAdapter(getActivity().getApplicationContext());
        mLoadable = new GroupsLoadable(getLoaderManager(), 0, new ListFragmentLoadableDecorator<ListGroups>(this, 0, this));
        setListAdapter(new ContentDecoratorAdapter(mLoadable, groupListAdapter));
        getListView().setOnScrollListener(new ListScrollListener(mLoadable));
        ColorDrawable sage = new ColorDrawable(this.getResources().getColor(android.R.color.black, null));
        getListView().setDivider(sage);
        getListView().setDividerHeight(5);
        getListView().setClickable(true);
        getListView().setItemsCanFocus(true);
        getListView().setFocusable(true);
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        getListView().setFocusableInTouchMode(true);

        mLoadable.init();
    }

    @Override
    public Loader<AsyncResourceLoader.Result<ListGroups>> onCreateLoader(int id, Bundle args) {
        return new SplitwiseGroupListLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<AsyncResourceLoader.Result<ListGroups>> loader, AsyncResourceLoader.Result<ListGroups> data) {
        groupListAdapter.setData(data.data, true);
    }

    @Override
    public void onLoaderReset(Loader<AsyncResourceLoader.Result<ListGroups>> loader) {
        groupListAdapter.setData(null, true);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //super.onListItemClick(l, v, position, id);
        //TODO make the group default on selection
        Group group = (Group) getListAdapter().getItem(position);
        Toast.makeText(getActivity(), group.getName(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDestroy() {
        mLoadable.destroy();
        super.onDestroy();
    }
}
