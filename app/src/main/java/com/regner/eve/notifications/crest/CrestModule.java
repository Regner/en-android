package com.regner.eve.notifications.crest;


import android.content.Context;

import com.tlabs.eve.crest.CrestNetwork;
import com.tlabs.eve.crest.net.CrestNetworkImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class CrestModule {

    private final CrestPreferences preferences;

    public CrestModule(final Context context) {
        this.preferences = new CrestPreferences(context.getApplicationContext());
    }

    @Provides
    @Singleton
    public CrestFacade provideCrestFacade() {
        final CrestNetwork network = new CrestNetworkImpl(preferences.getCrestInfo(), preferences.getClientName());
        return new CrestFacadeImpl(network);
    }
}
