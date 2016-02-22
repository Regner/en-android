package com.regner.eve.notifications.gcm;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MessageModule {

    private final Context context;

    public MessageModule(Context context) {
        this.context = context.getApplicationContext();
    }

    @Provides
    @Singleton
    public MessageFacade provideGCMFacade() {
        return new MessageFacadeImpl(context);
    }
}
