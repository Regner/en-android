package com.regner.eve.notifications.feeds;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;

interface FeedService {

    class FeedCategory {

        private String name;
        private List<Feed> topics;

        public String getName() {
            return name;
        }

        public List<Feed> getTopics() {
            return topics;
        }
    }

    @GET("/en-topic-settings/external/")
    Call<Map<String, FeedCategory>> getFeeds();

}
