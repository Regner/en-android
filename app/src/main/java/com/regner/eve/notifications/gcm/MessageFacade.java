package com.regner.eve.notifications.gcm;

import rx.Subscription;

public interface MessageFacade {

    interface Action {
        void onMessage(final Message message);
    }

    Subscription subscribe(final Action onNext);
}
