package com.regner.eve.notifications.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.regner.eve.notifications.ApplicationComponent;
import com.regner.eve.notifications.R;
import com.regner.eve.notifications.gcm.Message;

import javax.inject.Inject;

public class FeedDisplayFragment extends AbstractFragment implements MessageView {

    private FeedDisplayAdapter adapter = new FeedDisplayAdapter();

    @Inject
    MessagePresenter presenter;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final RecyclerView recycler = (RecyclerView)inflater.inflate(R.layout.recycler, null, false);
        recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(this.adapter);
        return recycler;
    }

    @Override
    public void onMessage(Message message) {
        this.adapter.addMessage(message);
    }
}
