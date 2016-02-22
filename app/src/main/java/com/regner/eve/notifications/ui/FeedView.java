package com.regner.eve.notifications.ui;

import com.regner.eve.notifications.feeds.FeedList;
import com.regner.eve.notifications.feeds.FeedSettings;

public interface FeedView {

    void show(final FeedList feeds);

    void show(final FeedSettings feeds);
}
