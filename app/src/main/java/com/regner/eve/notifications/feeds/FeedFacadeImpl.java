package com.regner.eve.notifications.feeds;

import android.content.Context;

import com.regner.eve.notifications.util.Log;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.IOException;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

final class FeedFacadeImpl implements FeedFacade {

    private final FeedService service;
    private final FeedPreferences preferences;

    public FeedFacadeImpl(final Context context) {
        this.preferences = new FeedPreferences(context);

        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        if (Log.D) {
            httpClient.addInterceptor(chain -> {
                    final okhttp3.Request rq = chain.request();
                    Log.d(ToStringBuilder.reflectionToString(rq));

                    final okhttp3.Response rs = chain.proceed(rq);
                    Log.d(ToStringBuilder.reflectionToString(rs));
                    return rs;
            });
        }

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(preferences.getURI())
                .addConverterFactory(JacksonConverterFactory.create())
                .client(httpClient.build())
                .build();

        this.service = retrofit.create(FeedService.class);
    }

    @Override
    public FeedList getFeeds() {
        try {
            final FeedList feeds = new FeedList();
            feeds.setFeeds(service.getFeeds().execute().body());
            for (Feed f: feeds.getFeeds().values()) {
                f.setEnabled(this.preferences.getFeedEnabled(f.getTopic()));
            }
            return feeds;
        }
        catch (IOException e) {
            Log.e(e.getLocalizedMessage(), e);
            return null;
        }
    }

    @Override
    public void setFeed(final Feed feed) {
        this.preferences.setFeedEnabled(feed.getTopic(), feed.getEnabled());
    }
}
