package com.regner.eve.notifications.ui;

import com.regner.eve.notifications.feeds.FeedFacade;
import com.regner.eve.notifications.util.RX;

import javax.inject.Inject;

public class FeedListPresenter extends ViewPresenter<FeedListView> {

    private final FeedFacade feeds;

    @Inject
    public FeedListPresenter(FeedFacade feeds) {
        this.feeds = feeds;
    }

    public void loadFeeds() {
        RX.subscribe(feeds::getFeeds, feeds -> getView().showList(feeds));
    }

    public void loadSettings() {
        RX.subscribe(feeds::getSettings, settings -> getView().showSettings(settings));
    }
}
