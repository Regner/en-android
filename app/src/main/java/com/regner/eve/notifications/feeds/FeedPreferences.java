package com.regner.eve.notifications.feeds;

import android.content.Context;

import com.regner.eve.notifications.util.PreferenceSupport;

public final class FeedPreferences {

    private static final String FEED_URI_KEY = FeedPreferences.class.getName() + ".uri";
    private static final String FEED_URI_DEFAULT = "http://104.155.15.132";

    private static final String FEED_ENABLED_KEY = FeedPreferences.class.getName() + ".enabled.";

    private final Context context;

    public FeedPreferences(Context context) {
        this.context = context.getApplicationContext();
    }

    public String getURI() {
        return PreferenceSupport.getString(context, FEED_URI_KEY, FEED_URI_DEFAULT);
    }

    public boolean getFeedEnabled(final String feed) {
        return PreferenceSupport.getBoolean(context, FEED_ENABLED_KEY + feed, false);
    }

    public void setFeedEnabled(final String feed, final boolean enabled) {
        PreferenceSupport.setBoolean(context, FEED_ENABLED_KEY + feed, enabled);
    }

}
