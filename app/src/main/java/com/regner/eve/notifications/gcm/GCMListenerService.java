package com.regner.eve.notifications.gcm;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.gcm.GcmListenerService;

import org.apache.commons.lang.StringUtils;

public class GCMListenerService extends GcmListenerService {

    public static final String ACTION = GCMListenerService.class.getName() + ".action";
    public static final String FROM = GCMListenerService.class.getName() + ".from";
    public static final String MESSAGE = GCMListenerService.class.getName() + ".message";

    @Override
    public void onMessageReceived(String from, Bundle data) {
        final String message = data.getString("notification");
        if (StringUtils.isBlank(message)) {
            return;
        }

        final Intent intent = new Intent(ACTION);
        intent.putExtra(FROM, from);
        intent.putExtra(MESSAGE, message);

        getApplicationContext().sendBroadcast(intent);
    }
}
