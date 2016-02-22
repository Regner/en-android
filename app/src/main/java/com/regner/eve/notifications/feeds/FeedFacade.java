package com.regner.eve.notifications.feeds;

import retrofit2.Response;

public interface FeedFacade {

    FeedList getFeeds();

    FeedSettings getFeedSettings();

    Response<?> saveFeedSettings(final FeedSettings settings);
}
