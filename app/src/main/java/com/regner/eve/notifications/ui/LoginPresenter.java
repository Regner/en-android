package com.regner.eve.notifications.ui;

import android.content.Context;
import android.net.Uri;

import com.regner.eve.notifications.crest.CrestFacade;
import com.regner.eve.notifications.crest.CrestStatus;
import com.regner.eve.notifications.gcm.GCMRegistrationService;

import javax.inject.Inject;

public class LoginPresenter extends ViewPresenter<LoginView> {

    private final CrestFacade crest;
    private final Context context;

    private CrestFacade.Authenticator authenticator;

    @Inject
    public LoginPresenter(final Context context, final CrestFacade crest) {
        this.crest = crest;
        this.context = context;
        this.authenticator = null;
    }

    public void login() {
        this.authenticator = null;

        this.crest.login(new CrestFacade.AuthenticatorView() {
            @Override
            public void start(String uri, CrestFacade.Authenticator authenticator) {
                LoginPresenter.this.authenticator = authenticator;
                getView().showLogin(uri);
            }

            @Override
            public void show(CrestStatus status) {
                getView().show(status);
                if (null == status) {
                    return;
                }
                GCMRegistrationService.register(
                    context,
                    Long.toString(status.getCharacterID()),
                    authenticator.getAuthenticated());
            }
        });
    }

    public boolean completeLogin(final Uri authData) {
        if (null == this.authenticator) {
            return false;
        }
        if (null == authData) {
            return false;
        }
        if (!"eve".equals(authData.getScheme())) {
            return false;
        }
        if (!"com.regner.eve.notifications".equals(authData.getHost())) {
            return false;
        }

        final String authCode = authData.getQueryParameter("code");
        this.authenticator.setAuthenticated(authCode);
        return true;
    }

    public boolean logout() {
        this.crest.logout();

        if (null == this.authenticator) {
            return false;
        }

        this.authenticator.setAuthenticated(null);
        this.authenticator = null;
        getView().show(null);
        return true;
    }
}
