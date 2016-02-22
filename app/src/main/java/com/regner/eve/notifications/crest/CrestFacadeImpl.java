package com.regner.eve.notifications.crest;

import com.regner.eve.notifications.util.Log;
import com.regner.eve.notifications.util.RX;
import com.tlabs.eve.crest.CrestNetwork;
import com.tlabs.eve.crest.model.CrestCharacterStatus;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

final class CrestFacadeImpl implements CrestFacade {

    private CrestNetwork crest;

    public CrestFacadeImpl(final CrestNetwork crest) {
        this.crest = crest;
    }

    @Override
    public void login(final AuthenticatorView callback) {
        final CrestNetwork.CrestAuthenticator auth = this.crest.getAuthenticator();

        final String uri = auth.start();
        if (StringUtils.isBlank(uri)) {
            return;
        }
        callback.start(uri, new Authenticator() {
            private String authCode;

            @Override
            public void setAuthenticated(String authCode) {
                this.authCode = authCode;

                RX.subscribe(
                () -> transform(auth.authenticate(authCode)),
                status -> {
                    Log.d(ToStringBuilder.reflectionToString(status));
                    callback.show(status);
                });
            }

            @Override
            public String getAuthenticated() {
                return authCode;
            }
        });
    }

    @Override
    public void logout() {
        final CrestNetwork.CrestAuthenticator auth = this.crest.getAuthenticator();
        auth.clear();
    }

    private static CrestStatus transform(final CrestCharacterStatus status) {
        if (null == status) {
            return null;
        }

        final CrestStatus returned = new CrestStatus();
        returned.setCharacterID(status.getCharacterID());
        returned.setCharacterName(status.getCharacterName());
        returned.setTokenType(status.getTokenType());
        returned.setHash(status.getHash());
        return returned;
    }
}
