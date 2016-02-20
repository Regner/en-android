package com.regner.eve.notifications;

import com.regner.eve.notifications.content.CrestModule;

public class Application extends android.app.Application {

    private ApplicationComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (appComponent == null) {
            appComponent =
                    DaggerApplicationComponent.builder()
                            .crestModule(new CrestModule(this))
                            .build();
        }
    }

    public ApplicationComponent getAppComponent() {
        return appComponent;
    }
}
