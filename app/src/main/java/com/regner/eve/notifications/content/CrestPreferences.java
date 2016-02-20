package com.regner.eve.notifications.content;

import android.content.Context;

import com.regner.eve.notifications.util.PreferenceSupport;
import com.tlabs.eve.crest.CrestNetwork;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
final class CrestPreferences {

    private static final String CLIENT_ID_KEY = CrestPreferences.class.getName() + ".clientID";
    private static final String CLIENT_ID_DEFAULT = "FILLME";

    private static final String CLIENT_CODE_KEY = CrestPreferences.class.getName() + ".clientCode";
    private static final String CLIENT_CODE_DEFAULT = "FILLME";

    private static final String CLIENT_URI_KEY = CrestPreferences.class.getName() + ".clientURI";
    private static final String CLIENT_URI_DEFAULT = "FILLME";

    private static final String CLIENT_NAME_KEY = CrestPreferences.class.getName() + ".clientName";
    private static final String CLIENT_NAME_DEFAULT = "EveNotifications";

    private static final String LOGIN_URL_KEY = CrestPreferences.class.getName() + ".loginURL";
    private static final String LOGIN_URL_DEFAULT = "https://login.eveonline.com";

    private static final String CREST_URL_KEY = CrestPreferences.class.getName() + ".crestURL";
    private static final String CREST_URL_DEFAULT = "https://public-crest.eveonline.com";

    private final Context context;
    private final CrestNetwork.CrestInfo networkInfo;

    @Inject
    public CrestPreferences(Context context) {
        this.context = context.getApplicationContext();
        this.networkInfo = new CrestNetwork.CrestInfo() {
            @Override
            public String getClientID() {
                return PreferenceSupport.getString(context, CLIENT_ID_KEY, CLIENT_ID_DEFAULT);
            }

            @Override
            public String getClientSecret() {
                return PreferenceSupport.getString(context, CLIENT_CODE_KEY, CLIENT_CODE_DEFAULT);
            }

            @Override
            public String getClientURI() {
                return PreferenceSupport.getString(context, CLIENT_URI_KEY, CLIENT_URI_DEFAULT);
            }

            @Override
            public String getLoginURL() {
                return PreferenceSupport.getString(context, LOGIN_URL_KEY, LOGIN_URL_DEFAULT);
            }

            @Override
            public String getCrestURL() {
                return PreferenceSupport.getString(context, CREST_URL_KEY, CREST_URL_DEFAULT);
            }
        };
    }

    final CrestNetwork.CrestInfo getCrestInfo() {
        return this.networkInfo;
    }

    final String getClientName() {
        return PreferenceSupport.getString(context, CLIENT_NAME_KEY, CLIENT_NAME_DEFAULT);
    }
}
