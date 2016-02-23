package com.regner.eve.notifications.feeds;

public interface FeedFacade {

    FeedList getFeeds();

    void setFeed(final Feed feed);
}
