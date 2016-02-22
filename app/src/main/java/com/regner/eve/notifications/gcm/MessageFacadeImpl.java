package com.regner.eve.notifications.gcm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.regner.eve.notifications.util.Log;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.IOException;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;
import rx.subjects.Subject;

final class MessageFacadeImpl implements MessageFacade {

    private static final ObjectMapper MAPPER;
    static {
        MAPPER = new ObjectMapper();
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private final Context context;
    private final Subject<Message, Message> subject;

    public MessageFacadeImpl(Context context) {
        this.context = context.getApplicationContext();

        final IntentFilter filter = new IntentFilter(GCMListenerService.ACTION);
        final LocalBroadcastManager bm = LocalBroadcastManager.getInstance(this.context);
        bm.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                subject.onNext(from(intent));
            }
        }, filter);

        this.subject = BehaviorSubject.create();
    }

    @Override
    public Subscription subscribe(final Action onNext) {
        return subject
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext::onMessage);
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

            Log.e("Message: " + ToStringBuilder.reflectionToString(message));
            return message;
        }
        catch (IOException e) {
            Log.e(e.getLocalizedMessage(), e);
            return null;
        }
    }
}
