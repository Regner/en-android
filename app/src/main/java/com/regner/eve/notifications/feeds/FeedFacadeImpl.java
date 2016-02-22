package com.regner.eve.notifications.feeds;

import android.content.Context;

import com.regner.eve.notifications.util.Log;

import java.io.IOException;
import java.util.Map;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

final class FeedFacadeImpl implements FeedFacade {

    private final Context context;
    private final FeedService service;

    private FeedToken token = null;

    public FeedFacadeImpl(Context context) {
        this.context = context;

        final FeedPreferences preferences = new FeedPreferences(context);
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(preferences.getURI())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        this.service = retrofit.create(FeedService.class);
    }

    @Override
    public FeedList getFeeds() {
        try {
            final Map<String, Feed> feeds = service.getFeeds().execute().body();
            final FeedList list = new FeedList();
            list.setFeeds(feeds);
            return list;
        }
        catch (IOException e) {
            Log.e(e.getLocalizedMessage(), e);
            return null;
        }
    }

    @Override
    public FeedSettings getFeedSettings() {
        if (null == this.token) {
            return null;
        }
        try {
            return this.service.getFeedSettings(
                    this.token.getAuthorization(),
                    this.token.getCharacterID())
                    .execute()
                    .body();
        }
        catch (IOException e) {
            Log.e(e.getLocalizedMessage(), e);
            return null;
        }
    }

    @Override
    public Response<String> saveFeedSettings(FeedSettings settings) {
        if (null == this.token) {
            return null;
        }
        try {
            return this.service.saveFeedSettings(
                    this.token.getAuthorization(),
                    this.token.getCharacterID(), settings).execute().body();
        }
        catch (IOException e) {
            Log.e(e.getLocalizedMessage(), e);
            return null;
        }
    }
}
