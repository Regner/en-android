package com.regner.eve.notifications.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.regner.eve.notifications.ApplicationComponent;
import com.regner.eve.notifications.R;
import com.regner.eve.notifications.crest.CrestStatus;
import com.regner.eve.notifications.feeds.FeedList;
import com.regner.eve.notifications.feeds.FeedSettings;
import com.regner.eve.notifications.util.Log;


import org.apache.commons.lang.builder.ToStringBuilder;

import javax.inject.Inject;

public class FeedListFragment extends AbstractFragment implements FeedListView {

    @Inject
    FeedListPresenter presenter;

    private FeedListAdapter adapter = new FeedListAdapter();

    @Override
    protected void inject(ApplicationComponent component) {
        component.inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.presenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        this.presenter.detachView(true);
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.presenter.loadFeeds();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final RecyclerView recycler = (RecyclerView)inflater.inflate(R.layout.recycler, null, false);
        recycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recycler.setAdapter(this.adapter);
        return recycler;
    }

    @Override
    public void showList(FeedList feeds) {
        Log.e("SHOW FEEDS " + ToStringBuilder.reflectionToString(feeds));
        this.adapter.setFeeds(feeds);
    }

    @Override
    public void showSettings(FeedSettings settings) {
        Log.e("SHOW SETTINGS " + ToStringBuilder.reflectionToString(settings));
        this.adapter.setSettings(settings);
    }

    protected void setCrestStatus(final CrestStatus status) {
        this.presenter.loadFeeds();
        if (null != status) {
            this.presenter.loadSettings();
        }
    }
}
