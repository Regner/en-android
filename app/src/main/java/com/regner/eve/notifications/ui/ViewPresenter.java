package com.regner.eve.notifications.ui;

import com.regner.eve.notifications.PerActivity;

import javax.inject.Singleton;

@PerActivity
@Singleton
public abstract class ViewPresenter<V> {

    private V view;

    public void attachView(V view) {
        this.view = view;
    }

    public void detachView(boolean retainInstance) {
        this.view = null;
    }

    protected final V getView() {
        return this.view;
    }
}
