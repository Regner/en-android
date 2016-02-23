package com.regner.eve.notifications.crest;

import android.net.Uri;

import com.tlabs.eve.crest.CrestNetwork;
import com.tlabs.eve.crest.model.CrestCharacterStatus;

final class CrestFacadeImpl implements CrestFacade {

    private CrestNetwork crest;

    public CrestFacadeImpl(final CrestNetwork crest) {
        this.crest = crest;
    }

    @Override
    public String start() {
        return crest.getAuthenticator().start();
    }

    @Override
    public CrestStatus login(final Uri authData) {
        if (null == authData) {
            return null;
        }
        if (!"eve".equals(authData.getScheme())) {
            return null;
        }
        if (!"com.regner.eve.notifications".equals(authData.getHost())) {
            return null;
        }

        final String authCode = authData.getQueryParameter("code");
        final CrestNetwork.CrestAuthenticator auth = this.crest.getAuthenticator();

        final CrestCharacterStatus status = auth.authenticate(authCode);
        return transform(status, auth.getToken());
    }

    @Override
    public void logout() {
        final CrestNetwork.CrestAuthenticator auth = this.crest.getAuthenticator();
        auth.clear();
    }

    private static CrestStatus transform(final CrestCharacterStatus status, final String accessToken) {
        if (null == status) {
            return null;
        }

        final CrestStatus returned = new CrestStatus();
        returned.setCharacterID(status.getCharacterID());
        returned.setCharacterName(status.getCharacterName());
        returned.setToken(accessToken);
        return returned;
    }
}
