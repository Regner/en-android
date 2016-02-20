package com.regner.eve.notifications.ui;

import android.content.Intent;
import android.net.Uri;

import com.regner.eve.notifications.ApplicationComponent;
import com.tlabs.eve.crest.model.CrestCharacterStatus;

import javax.inject.Inject;

public class MainActivity extends AbstractActivity implements LoginView {

    @Inject
    LoginPresenter login;

    @Override
    protected void inject(ApplicationComponent component) {
        component.inject(this);
    }

    @Override
    public void showLogin(String uri) {
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }

    @Override
    public void showStatus(CrestCharacterStatus status) {
        if (null == status) {
            setTitle("Not logged in");
        }
        else {
            setTitle(status.getCharacterName());
        }
    }


    @Override
    protected void onNewIntent(final Intent intent) {
        super.onNewIntent(intent);
        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            login.completeLogin(intent.getData());
        }
    }
}
