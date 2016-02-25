package com.regner.eve.notifications.gcm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.regner.eve.notifications.util.Log;

import org.apache.commons.lang.StringUtils;

import java.io.IOException;

public abstract class MessageReceiver extends BroadcastReceiver {

    private static final ObjectMapper MAPPER;
    static {
        MAPPER = new ObjectMapper();
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public abstract void onReceive(final Context context, final Message message);

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("MessageReceiver.onReceive: " + intent.getAction());

        final Message message = from(intent);
        if (null != message) {
            onReceive(context, message);
        }
    }

    private static Message from(final Intent intent) {
        String text = intent.getStringExtra(GCMListenerService.MESSAGE);
        if (StringUtils.isBlank(text)) {
            Log.w("No text in GCM message");
            return null;
        }
        try {
            Message message = MAPPER.readValue(StringUtils.removeStart(text, "notification ="), Message.class);
            message.setFrom(intent.getStringExtra(GCMListenerService.FROM));
            return message;
        }
        catch (IOException e) {
            Log.e(e.getLocalizedMessage(), e);
            return null;
        }
    }
}
