package com.regner.eve.notifications.ui;

import com.regner.eve.notifications.feeds.Feed;
import com.regner.eve.notifications.feeds.FeedFacade;
import com.regner.eve.notifications.gcm.MessageFacade;
import com.regner.eve.notifications.util.Log;
import com.regner.eve.notifications.util.RX;

import org.apache.commons.lang.builder.ToStringBuilder;

import javax.inject.Inject;

public class FeedListPresenter extends ViewPresenter<FeedListView> {

    private final FeedFacade feeds;
    private final MessageFacade messages;

    @Inject
    public FeedListPresenter(FeedFacade feeds, MessageFacade messages) {
        this.feeds = feeds;
        this.messages = messages;
    }

    public void loadFeeds() {
        RX.subscribe(feeds::getFeeds, feeds -> {
            for (Feed f: feeds.getFeeds().values()) {
                Log.e("ENABED " + ToStringBuilder.reflectionToString(f));
                if (f.getEnabled()) {
                    this.messages.subscribe(f.getTopic(), message -> getView().showMessage(message));
                }
            }
            getView().showList(feeds);
        });
    }

    public void setEnabled(final Feed feed, final boolean enabled) {
        if (enabled) {
            this.messages.subscribe(feed.getTopic(), message -> getView().showMessage(message));
            feed.setEnabled(true);
        }
        else {
            this.messages.unsubscribe(feed.getTopic());
            feed.setEnabled(false);
        }
        feeds.setFeed(feed);
    }
}
