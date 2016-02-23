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
    private final Context context;

    private FeedToken token = null;

    public FeedFacadeImpl(final Context context) {
        this.context = context;

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

    @Override
    public FeedSettings getSettings() {
        if (null == this.token) {
            return null;
        }
        try {
            final Map<String, Boolean> settings =
                this.service.getFeedSettings(
                    "Bearer " + this.token.getAuthorization(),
                    this.token.getCharacterID())
                    .execute()
                    .body();
            return new FeedSettings().setSettings(settings);
        }
        catch (IOException e) {
            Log.e(e.getLocalizedMessage(), e);
            return null;
        }
    }

    @Override
    public void saveSettings(FeedSettings settings) {
        if (null == this.token) {
            return;
        }
        try {
            this.service.saveFeedSettings(
                    "Bearer " + this.token.getAuthorization(),
                    this.token.getCharacterID(), settings.getSettings()).execute().body();
        }
        catch (IOException e) {
            Log.e(e.getLocalizedMessage(), e);
        }
    }

    @Override
    public boolean register(final String charID, final String authToken) {
        Log.e("Register " + ToStringBuilder.reflectionToString(this.token));

        this.token = registerGCM(charID, authToken);
        if (null == token) {
            return false;
        }
        registerSettings();
        return true;
    }

    private FeedSettings registerSettings() {
        final FeedSettings existing = getSettings();
        if ((null != existing) && !existing.getSettings().isEmpty()) {
            return existing;
        }

        final FeedSettings settings = new FeedSettings();
        final FeedList feeds = getFeeds();
        for (Map.Entry<String, Feed> f: feeds.getFeeds().entrySet()) {
            settings.setSettings(f.getKey(), false);
        }

        settings.setSettings("eve-news", true);
        Log.e("SAVE SETTUBGS " + ToStringBuilder.reflectionToString(settings));
        saveSettings(settings);
        return getSettings();
    }

    private FeedToken registerGCM(final String charID, final String authToken) {
        final FeedToken token = new FeedToken()
                .setCharacterID(charID)
                .setAuthorization(authToken);

        final InstanceID instanceID = InstanceID.getInstance(this.context);
        try {
            final String gcmToken = instanceID.getToken(
                    this.context.getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE,
                    null);

            Log.e("Gcm Token:" + gcmToken);

            final Response<String> registered = this.service
                    .saveFeedToken("Bearer " + authToken, charID, token.setToken(gcmToken))
                    .execute();
            Log.e(ToStringBuilder.reflectionToString(registered));
            //return (registered.isSuccess()) ? token : null;
            return token;
        }
        catch (IOException e) {
            Log.e(e.getLocalizedMessage());
            return null;
        }
    }

}
