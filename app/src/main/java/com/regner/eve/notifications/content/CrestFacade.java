package com.regner.eve.notifications.content;

import com.tlabs.eve.crest.model.CrestCharacterStatus;

public interface CrestFacade {

    interface Authenticator {

        void setAuthenticated(final String authCode);
    }

    interface AuthenticatorView {

        void show(final String uri, final Authenticator authenticator);

        void show(final CrestCharacterStatus status);

    }

    public void login(final AuthenticatorView view);

}
