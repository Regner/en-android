package com.regner.eve.notifications.ui;

import android.net.Uri;

import com.regner.eve.notifications.crest.CrestFacade;
import com.regner.eve.notifications.crest.CrestStatus;
import com.regner.eve.notifications.feeds.FeedFacade;
import com.regner.eve.notifications.util.RX;

import org.apache.commons.lang.StringUtils;

import javax.inject.Inject;

public class LoginPresenter extends ViewPresenter<LoginView> {

    private final CrestFacade crest;
    private final FeedFacade feeds;

    @Inject
    public LoginPresenter(final FeedFacade feeds, final CrestFacade crest) {
        this.crest = crest;
        this.feeds = feeds;
    }

    public void login() {
        final String uri = this.crest.start();
        if (StringUtils.isNotBlank(uri)) {
            getView().showLogin(uri);
        }
    }

    public void register(final Uri authData) {
        RX.subscribe(() -> {
            final CrestStatus status = this.crest.login(authData);
            if (null != status) {
                this.feeds.register(Long.toString(status.getCharacterID()), authData.getQueryParameter("code"));
            }
            return status;
        },
        s -> getView().show(s));
    }

    public void logout() {
        this.crest.logout();
        getView().show(null);
    }
}
