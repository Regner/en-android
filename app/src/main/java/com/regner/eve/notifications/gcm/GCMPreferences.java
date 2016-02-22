package com.regner.eve.notifications.gcm;

import android.content.Context;

import com.regner.eve.notifications.util.PreferenceSupport;

final class GCMPreferences {

    static final String GCM_CHARACTER = GCMPreferences.class.getName() + ".charID";


    private final Context context;

    public GCMPreferences(Context context) {
        this.context = context.getApplicationContext();
    }

    final String getCharacterID() {
        return PreferenceSupport.getString(context, GCM_CHARACTER, null);
    }

    final void setCharacterID(final String charID) {
        PreferenceSupport.setString(context, GCM_CHARACTER, charID);
    }
}
