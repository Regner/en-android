package com.regner.eve.notifications;

import com.regner.eve.notifications.crest.CrestModule;
import com.regner.eve.notifications.feeds.FeedModule;
import com.regner.eve.notifications.gcm.GCMModule;
import com.regner.eve.notifications.ui.MainActivity;
import com.regner.eve.notifications.ui.MainFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        CrestModule.class,
        GCMModule.class,
        FeedModule.class,
        ApplicationModule.class})
public interface ApplicationComponent {

    void inject(final MainActivity activity);

    void inject(final MainFragment fragment);
}
