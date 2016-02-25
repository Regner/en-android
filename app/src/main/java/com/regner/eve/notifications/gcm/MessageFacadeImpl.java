package com.regner.eve.notifications.gcm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.app.NotificationCompat;
import android.net.Uri;
import android.app.PendingIntent;
import android.app.Notification;
import android.app.NotificationManager;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.regner.eve.notifications.R;
import com.regner.eve.notifications.util.Log;
import com.regner.eve.notifications.util.RX;

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
    private String token;

    public MessageFacadeImpl(Context context) {
        this.context = context.getApplicationContext();

        final IntentFilter filter = new IntentFilter(GCMListenerService.ACTION);
        final LocalBroadcastManager bm = LocalBroadcastManager.getInstance(this.context);
        bm.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.e(ToStringBuilder.reflectionToString(intent));
                subject.onNext(from(intent));
            }
        }, filter);

        this.subject = BehaviorSubject.create();
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

        Log.e("registerTopic: topic=" + topic + "; token=" + this.token);
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
        Log.e("unregisterTopic: topic=" + topic + "; token=" + this.token);
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
        Log.e("registerGCM");
        final InstanceID instanceID = InstanceID.getInstance(this.context);
        try {
            final String gcmToken = instanceID.getToken(
                    this.context.getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE,
                    null);

            Log.e("Gcm Token:" + gcmToken);
            return gcmToken;
        }
        catch (IOException e) {
            Log.e(e.getLocalizedMessage(), e);
            return null;
        }
    }

    private void sendNotification(Message message) {
        Intent notificationIntent = new Intent(Intent.ACTION_VIEW);
        notificationIntent.setData(Uri.parse(message.getUrl()));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context.getApplicationContext())
                        .setSmallIcon(R.drawable.ic_account)
                        .setContentTitle(message.getTitle())
                        .setContentText(message.getSubtitle())
                        .setContentIntent(pendingIntent);

        // Need to add the message ID here
        int notificationId = 001;

        NotificationManager notifyMgr =
                (NotificationManager) context.getApplicationContext()
                        .getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = builder.build();

        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;

        notifyMgr.notify(notificationId, notification);
    }

    private Message from(final Intent intent) {
        String text = intent.getStringExtra(GCMListenerService.MESSAGE);
        if (StringUtils.isBlank(text)) {
            Log.w("No text in GCM message");
            return null;
        }
        try {
            Message message = MAPPER.readValue(StringUtils.removeStart(text, "notification ="), Message.class);
            message.setFrom(intent.getStringExtra(GCMListenerService.FROM));

            this.sendNotification(message);

            Log.e("Message: " + ToStringBuilder.reflectionToString(message));
            return message;
        }
        catch (IOException e) {
            Log.e(e.getLocalizedMessage(), e);
            return null;
        }
    }
}
