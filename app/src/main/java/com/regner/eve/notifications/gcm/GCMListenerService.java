package com.regner.eve.notifications.gcm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.google.android.gms.gcm.GcmListenerService;

public class GCMListenerService extends GcmListenerService {

    public static final String ACTION = GCMListenerService.class.getName() + ".action";
    public static final String FROM = GCMListenerService.class.getName() + ".from";
    public static final String MESSAGE = GCMListenerService.class.getName() + ".message";

    @Override
    public void onMessageReceived(String from, Bundle data) {
        final Intent intent = new Intent(ACTION);
        intent.getExtras().putString(FROM, from);
        intent.getExtras().putString(MESSAGE, data.getString("message"));

        final LocalBroadcastManager bm = LocalBroadcastManager.getInstance(this);
        bm.sendBroadcast(intent);
    }


}
