package com.regner.eve.notifications.feeds;

import android.content.Context;

import com.regner.eve.notifications.util.PreferenceSupport;

public final class FeedPreferences {

    private static final String FEED_URI_KEY = FeedPreferences.class.getName() + ".uri";
    private static final String FEED_URI_DEFAULT = "http://104.155.15.132";

    private final Context context;

    public FeedPreferences(Context context) {
        this.context = context.getApplicationContext();
    }

    public final String getURI() {
        return PreferenceSupport.getString(context, FEED_URI_KEY, FEED_URI_DEFAULT);
    }

}
