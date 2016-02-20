package com.regner.eve.notifications.ui;

import android.net.Uri;

import com.regner.eve.notifications.content.CrestFacade;
import com.tlabs.eve.crest.model.CrestCharacterStatus;

import javax.inject.Inject;

public class LoginPresenter extends ViewPresenter<LoginView> {

    private final CrestFacade crest;
    private CrestFacade.Authenticator authenticator;

    @Inject
    public LoginPresenter(final CrestFacade crest) {
        this.crest = crest;
        this.authenticator = null;
    }

    public void startLogin() {
        this.authenticator = null;

        this.crest.login(new CrestFacade.AuthenticatorView() {
            @Override
            public void show(String uri, CrestFacade.Authenticator authenticator) {
                LoginPresenter.this.authenticator = authenticator;
                getView().showLogin(uri);
            }

            @Override
            public void set(CrestCharacterStatus status) {
                getView().showStatus(status);
            }
        });
    }

    public boolean completeLogin(final Uri authData) {
        if (null == this.authenticator) {
            return false;
        }
        this.authenticator.setAuthenticated(authData.getQueryParameter("code"));
        return true;
    }

    public boolean cancelLogin() {
        if (null == this.authenticator) {
            return false;
        }

        this.authenticator.setAuthenticated(null);
        this.authenticator = null;
        return true;
    }
}
