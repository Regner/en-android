package com.regner.eve.notifications.feeds;


import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class FeedModule {

    private final Context context;

    public FeedModule(final Context context) {
        this.context = context.getApplicationContext();
    }

    @Provides
    @Singleton
    public FeedService provideFeedService() {
        final FeedPreferences preferences = new FeedPreferences(context);
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(preferences.getURI())
                .build();

        return retrofit.create(FeedService.class);
    }

    @Provides
    @Singleton
    public FeedFacade provideFeedFacade() {
        return new FeedFacadeImpl(this.context);
    }
}
