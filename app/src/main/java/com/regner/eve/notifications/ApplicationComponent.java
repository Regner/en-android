package com.regner.eve.notifications;

import com.regner.eve.notifications.content.CrestModule;
import com.regner.eve.notifications.gcm.GCMModule;
import com.regner.eve.notifications.ui.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {CrestModule.class, GCMModule.class})
public interface ApplicationComponent {

    void inject(final MainActivity activity);
}
