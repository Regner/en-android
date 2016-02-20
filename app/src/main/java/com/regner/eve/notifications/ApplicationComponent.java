package com.regner.eve.notifications;

import com.regner.eve.notifications.content.CrestModule;
import com.regner.eve.notifications.gcm.GCMModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {CrestModule.class, GCMModule.class})
public interface ApplicationComponent {
}
