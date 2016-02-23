package com.regner.eve.notifications.ui;

import com.regner.eve.notifications.feeds.FeedList;
import com.regner.eve.notifications.gcm.Message;

public interface FeedListView {

    void showList(final FeedList feeds);

    void showMessage(final Message message);

}
