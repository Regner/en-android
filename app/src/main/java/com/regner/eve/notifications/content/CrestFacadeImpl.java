package com.regner.eve.notifications.content;

import com.tlabs.eve.crest.CrestNetwork;
import com.tlabs.eve.crest.model.CrestCharacterStatus;

import org.apache.commons.lang.StringUtils;

final class CrestFacadeImpl implements CrestFacade {

    private CrestNetwork crest;
    private CrestCharacterStatus status;

    public CrestFacadeImpl(final CrestNetwork crest) {
        this.crest = crest;
    }

    @Override
    public void login(final AuthenticatorView callback) {
        final CrestNetwork.CrestAuthenticator auth = this.crest.getAuthenticator();
        final String uri = auth.start();

        if (StringUtils.isBlank(uri)) {
            callback.set(null);
        }

        this.status = null;
        callback.show(uri, authCode -> {
            if (StringUtils.isBlank(authCode)) {
                auth.clear();
            } else {
                status = auth.authenticate(authCode);
            }
            callback.set(status);
        });
    }
}
