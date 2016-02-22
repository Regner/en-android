package com.regner.eve.notifications.crest;


public interface CrestFacade {

    interface Authenticator {

        void setAuthenticated(final String authCode);

        String getAuthenticated();
    }

    interface AuthenticatorView {

        void start(final String uri, final Authenticator authenticator);

        void show(final CrestStatus status);

    }

    void login(final AuthenticatorView view);

    void logout();

}
