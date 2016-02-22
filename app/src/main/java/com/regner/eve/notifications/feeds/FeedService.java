package com.regner.eve.notifications.feeds;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;

interface FeedService {

    @GET("/en-rss/external/")
    Call<Map<String, Feed>> getFeeds();

    @GET("/en-rss/external/characters/{charID}/")
    Call<FeedSettings> getFeedSettings(
            @Header("Authorization") String authorization,
            @Path("charID") final String charID);

    @PUT("/en-rss/external/characters/{charID}/")
    Call<Response<String>> saveFeedSettings(
            @Header("Authorization") String authorization,
            @Path("charID") final String charID,
            @Body final FeedSettings settings);
}
