package com.regner.eve.notifications.feeds;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;

interface FeedService {

    @GET("/en-rss/external/")
    Call<Map<String, Feed>> getFeeds();

}
