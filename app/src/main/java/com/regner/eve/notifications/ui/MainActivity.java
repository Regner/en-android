package com.regner.eve.notifications.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.regner.eve.notifications.ApplicationComponent;
import com.regner.eve.notifications.R;
import com.regner.eve.notifications.crest.CrestStatus;
import com.regner.eve.notifications.util.Snacks;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class MainActivity extends AbstractActivity<MainFragment> implements StatusView, LoginView {

    @Inject
    LoginPresenter login;

    private CrestStatus status;

    @Override
    protected MainFragment createFragment() {
        return new MainFragment();
    }

    @Override
    protected void inject(ApplicationComponent component) {
        component.inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
        setTitle(R.string.title_logged_out);
        setDescription(R.string.title_logged_out_description);
        ButterKnife.bind(this);
        this.login.attachView(this);
    }

    @Override
    protected void onDestroy() {
        this.login.detachView(false);
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    public void showLogin(String uri) {
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        final boolean loggedIn = (null != this.status);
        Activities.enableMenuItem(R.id.menuLogin, menu, !loggedIn, !loggedIn);
        Activities.enableMenuItem(R.id.menuLogout, menu, loggedIn, loggedIn);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuLogin:
                this.login.login();
                return true;
            case R.id.menuLogout:
                this.login.logout();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void show(CrestStatus status) {
        this.status = status;
        if (null == status) {
            setTitle(R.string.title_logged_out);
            setDescription(R.string.title_logged_out_description);
            Snacks.show(this, R.string.snack_logged_out);
        }
        else {
            Snacks.show(this, r(R.string.snack_logged_in, status.getCharacterName()));
            setTitle(r(R.string.title_logged_in, status.getCharacterName()));
            setDescription(R.string.title_logged_in_description);
        }
        supportInvalidateOptionsMenu();
    }

    @Override
    protected void onNewIntent(final Intent intent) {
        super.onNewIntent(intent);
        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            login.completeLogin(intent.getData());
        }
    }
}
