package com.regner.eve.notifications;

import com.regner.eve.notifications.feeds.FeedModule;
import com.regner.eve.notifications.gcm.MessageModule;
import com.regner.eve.notifications.ui.FeedDisplayFragment;
import com.regner.eve.notifications.ui.MainActivity;
import com.regner.eve.notifications.ui.FeedListFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        MessageModule.class,
        FeedModule.class,
        ApplicationModule.class})
public interface ApplicationComponent {

    void inject(final FeedListFragment fragment);

    void inject(final FeedDisplayFragment fragment);
}
