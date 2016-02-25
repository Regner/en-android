package com.regner.eve.notifications.gcm;

import android.content.Context;
import android.content.IntentFilter;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.regner.eve.notifications.R;
import com.regner.eve.notifications.util.Log;
import com.regner.eve.notifications.util.RX;

import java.io.IOException;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;
import rx.subjects.Subject;

final class MessageFacadeImpl implements MessageFacade {

    private final Context context;
    private final Subject<Message, Message> subject;
    private String token;

    public MessageFacadeImpl(Context context) {
        this.context = context.getApplicationContext();

        final IntentFilter filter = new IntentFilter(GCMListenerService.ACTION);
        this.subject = BehaviorSubject.create();
        context.registerReceiver(new MessageReceiver() {
             @Override
             public void onReceive(Context context, Message message) {
                 subject.onNext(message);
             }
         },
         filter);
    }

    @Override
    public Subscription subscribe(final String topic, final Action onNext) {
        RX.subscribe(() -> registerTopic(topic));
        return subject
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext::onMessage);
    }

    @Override
    public void unsubscribe(String topic) {
        RX.subscribe(() -> unregisterTopic(topic));
    }

    private Void registerTopic(final String topic) {
        if (null == this.token) {
            this.token = registerGCM();
        }

        if (null == this.token) {
            return null;
        }
        try {
            GcmPubSub pubSub = GcmPubSub.getInstance(this.context);
            pubSub.subscribe(token, "/topics/" + topic, null);
        }
        catch (IOException e) {
            Log.e(e.getLocalizedMessage(), e);
        }
        return null;
    }

    private Void unregisterTopic(final String topic) {
        if (null == this.token) {
            return null;
        }
        try {
            GcmPubSub pubSub = GcmPubSub.getInstance(this.context);
            pubSub.unsubscribe(token, "/topics/" + topic);
        }
        catch (IOException e) {
            Log.e(e.getLocalizedMessage(), e);
        }
        return null;
    }

    private String registerGCM() {
        final InstanceID instanceID = InstanceID.getInstance(this.context);
        try {
            final String gcmToken = instanceID.getToken(
                    this.context.getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE,
                    null);
            return gcmToken;
        }
        catch (IOException e) {
            Log.e(e.getLocalizedMessage(), e);
            return null;
        }
    }

}
