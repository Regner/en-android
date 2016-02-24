package com.regner.eve.notifications.ui;

import com.regner.eve.notifications.feeds.Feed;
import com.regner.eve.notifications.gcm.Message;

import java.util.List;

public interface FeedListView {

    void showList(final List<Feed> feeds);

    void showMessage(final Message message);

}
