package com.regner.eve.notifications.feeds;

public interface FeedFacade {

    FeedList getFeeds();

    FeedSettings getSettings();

    void saveSettings(final FeedSettings settings);

    boolean register(final String charID, final String authToken);
}
