package com.regner.eve.notifications.ui;

import com.regner.eve.notifications.feeds.FeedList;
import com.regner.eve.notifications.feeds.FeedSettings;

public interface FeedListView {

    void showList(final FeedList feeds);

    void showSettings(final FeedSettings feeds);
}
