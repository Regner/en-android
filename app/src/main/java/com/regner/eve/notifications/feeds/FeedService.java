package com.regner.eve.notifications.feeds;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;

interface FeedService {

    @GET("/en-rss/external/")
    Call<Map<String, Feed>> getFeeds();

}
