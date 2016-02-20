package com.regner.eve.notifications.gcm;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class GCMModule {

    private final Context context;

    public GCMModule(Context context) {
        this.context = context.getApplicationContext();
    }

    @Provides
    @Singleton
    public GCMFacade provideGCMFacade() {
        return new GCMFacadeImpl(context);
    }
}
