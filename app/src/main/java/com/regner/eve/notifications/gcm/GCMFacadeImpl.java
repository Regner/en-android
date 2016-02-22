package com.regner.eve.notifications.gcm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;
import rx.subjects.Subject;

final class GCMFacadeImpl implements GCMFacade {

    private final Context context;
    private final Subject<GCMMessage, GCMMessage> subject;

    public GCMFacadeImpl(Context context) {
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
    public Subscription subscribe(final GCMAction onNext) {
        return subject
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext::onMessage);
    }

    private static GCMMessage from(final Intent intent) {
        final GCMMessage message = new GCMMessage();
        message.setFrom(intent.getStringExtra(GCMListenerService.FROM));
        message.setText(intent.getStringExtra(GCMListenerService.MESSAGE));
        return message;
    }
}
