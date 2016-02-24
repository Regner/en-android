package com.regner.eve.notifications.feeds;

import java.util.List;

public interface FeedFacade {

    List<Feed> getFeeds();

    void setFeed(final Feed feed);
}
