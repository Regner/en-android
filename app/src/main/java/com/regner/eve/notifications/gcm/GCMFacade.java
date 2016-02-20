package com.regner.eve.notifications.gcm;

import rx.Subscription;

public interface GCMFacade {

    interface GCMAction {
        void onMessage(final GCMMessage message);
    }

    Subscription subscribe(final GCMAction onNext);
}
