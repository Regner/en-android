package com.regner.eve.notifications.feeds;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FeedList {

    private Map<String, Feed> feeds = new HashMap<>();

    public Map<String, Feed> getFeeds() {
        return Collections.unmodifiableMap(this.feeds);
    }

    public void setFeeds(Map<String, Feed> feeds) {
        this.feeds.clear();
        if (null != feeds) {
            this.feeds.putAll(feeds);
        }
    }
}
