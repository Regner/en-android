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
    public void attachView(MessageView view) {
        super.attachView(view);
        this.subscription = messages.subscribe(m -> getView().onMessage(m));
    }

    @Override
    public void detachView(boolean retainInstance) {
        this.subscription.unsubscribe();
        this.subscription = null;
        super.detachView(retainInstance);
    }
}
