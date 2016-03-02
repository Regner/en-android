package com.regner.eve.notifications.gcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.regner.eve.notifications.R;

public class NotificationService extends MessageReceiver {

    @Override
    public void onReceive(Context context, Message message) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context.getApplicationContext())
                        .setSmallIcon(R.mipmap.ic_launcher)//FIXME change this icon
                        .setContentTitle(message.getTitle())
                        .setContentText(message.getSubtitle())
                        .setShowWhen(true)
                        .setWhen(message.getTime());

        if (message.getUrl() != null) {
            final Intent notificationIntent = new Intent(Intent.ACTION_VIEW);
            notificationIntent.setData(Uri.parse(message.getUrl()));
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

            builder.setContentIntent(pendingIntent);
        }

        if (message.getExtraText() != null) {
            builder.setStyle(new NotificationCompat.BigTextStyle()
                    .setBigContentTitle(message.getTitle())
                    .setSummaryText(message.getSubtitle())
                    .bigText(message.getExtraText()));
        }

        final NotificationManager notifyMgr =
                (NotificationManager) context.getApplicationContext()
                        .getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = builder.build();

        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;

        notifyMgr.notify(message.getId(), notification);
    }
}
