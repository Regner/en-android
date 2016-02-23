package com.regner.eve.notifications.feeds;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FeedList {

    private Map<String, Feed> feeds = new HashMap<>();

    public Map<String, Feed> getFeeds() {
        return Collections.unmodifiableMap(this.feeds);
    }

    public FeedList setFeeds(Map<String, Feed> feeds) {
        this.feeds.clear();
        if (null != feeds) {
            this.feeds.putAll(feeds);
        }
        for (Map.Entry<String, Feed> e: this.feeds.entrySet()) {
            e.getValue().setTopic(e.getKey());
        }
        return this;
    }
}
