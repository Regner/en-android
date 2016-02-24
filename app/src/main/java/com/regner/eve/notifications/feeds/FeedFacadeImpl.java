package com.regner.eve.notifications.feeds;

import android.content.Context;

import com.regner.eve.notifications.util.Log;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
    public List<Feed> getFeeds() {
        try {
            final Map<String, FeedService.FeedCategory> feeds = service.getFeeds().execute().body();
            final List<Feed> returned = new LinkedList<>();
            for (Map.Entry<String, FeedService.FeedCategory> e: feeds.entrySet()) {
                for (Feed f: e.getValue().getTopics()) {
                    f.setCategoryID(e.getKey());
                    f.setCategoryName(e.getValue().getName());
                    f.setEnabled(this.preferences.getFeedEnabled(f.getTopic()));

                    returned.add(f);
                }
            }
            return returned;
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
