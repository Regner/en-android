package com.regner.eve.notifications.feeds;

import android.content.Context;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.regner.eve.notifications.R;
import com.regner.eve.notifications.util.Log;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.IOException;
import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

final class FeedFacadeImpl implements FeedFacade {

    private final FeedService service;

    public FeedFacadeImpl(final Context context) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        if (Log.D) {
            httpClient.addInterceptor(chain -> {
                    final okhttp3.Request rq = chain.request();
                    Log.d(ToStringBuilder.reflectionToString(rq));

                    final okhttp3.Response rs = chain.proceed(rq);
                    Log.d(ToStringBuilder.reflectionToString(rs));
                    return rs;
            });
        }

        final FeedPreferences preferences = new FeedPreferences(context);
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

}
