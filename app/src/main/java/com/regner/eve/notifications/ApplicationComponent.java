package com.regner.eve.notifications;

import com.regner.eve.notifications.content.CrestModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {CrestModule.class})
public interface ApplicationComponent {
}
