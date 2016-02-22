package com.regner.eve.notifications;

import com.regner.eve.notifications.crest.CrestModule;
import com.regner.eve.notifications.feeds.FeedModule;
import com.regner.eve.notifications.gcm.GCMModule;

public class Application extends android.app.Application {

    private ApplicationComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (appComponent == null) {
            appComponent =
                    DaggerApplicationComponent
                    .builder()
                    .crestModule(new CrestModule(this))
                    .feedModule(new FeedModule(this))
                    .gCMModule(new GCMModule(this))
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
    }

    public ApplicationComponent getAppComponent() {
        return appComponent;
    }
}
