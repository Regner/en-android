package com.regner.eve.notifications.ui;

import com.regner.eve.notifications.gcm.MessageFacade;

import javax.inject.Inject;

import rx.Subscription;

public class MessagePresenter extends ViewPresenter<MessageView> {

    private final MessageFacade messages;

    private Subscription subscription;

    @Inject
    public MessagePresenter(final MessageFacade messages) {
        this.messages = messages;
        this.subscription = null;
    }

    @Override
    public void detachView(boolean retainInstance) {
        unsubscribe();
        super.detachView(retainInstance);
    }

    public void subscribe(final String topic) {
        if (null != this.subscription) {
            this.subscription.unsubscribe();
        }
        this.subscription = messages.subscribe(topic, m -> getView().onMessage(m));
    }

    public void unsubscribe() {
        if (null != this.subscription) {
            this.subscription.unsubscribe();
        }
        this.subscription = null;
    }
}
