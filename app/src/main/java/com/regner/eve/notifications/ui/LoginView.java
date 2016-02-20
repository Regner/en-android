package com.regner.eve.notifications.ui;

import com.tlabs.eve.crest.model.CrestCharacterStatus;

public interface LoginView {

    void showLogin(final String uri);

    void showStatus(final CrestCharacterStatus status);
}
