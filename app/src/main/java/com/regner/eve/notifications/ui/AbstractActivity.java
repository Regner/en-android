package com.regner.eve.notifications.ui;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.regner.eve.notifications.Application;
import com.regner.eve.notifications.ApplicationComponent;
import com.regner.eve.notifications.R;
import com.regner.eve.notifications.util.Log;

public abstract class AbstractActivity<F extends AbstractFragment> extends AppCompatActivity implements TitleView {

    private F fragment;

    protected abstract void inject(final ApplicationComponent component);

    protected abstract F createFragment();

    protected final F getFragment() {
        return fragment;
    }

    @Override
    public void setTitle(int rId) {
        final ActionBar ab = getSupportActionBar();
        if (null == ab) {
            return;
        }
        ab.setTitle(rId);
    }

    @Override
    public void setDescription(int rId) {
        final ActionBar ab = getSupportActionBar();
        if (null == ab) {
            return;
        }
        ab.setSubtitle(rId);
    }

    @Override
    public final void setTitle(String title) {
        final ActionBar ab = getSupportActionBar();
        if (null == ab) {
            return;
        }
        ab.setTitle(title);
    }

    @Override
    public final void setDescription(String title) {
        final ActionBar ab = getSupportActionBar();
        if (null == ab) {
            return;
        }
        ab.setSubtitle(title);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);

        final ApplicationComponent appComponent = ((Application) getApplication()).getAppComponent();
        inject(appComponent);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setupToolbar();

        final FragmentManager fm = getSupportFragmentManager();

        this.fragment = (F)fm.findFragmentById(R.id.fragmentContainer);
        if (this.fragment == null) {
            this.fragment = createFragment();
            fm.beginTransaction().add(R.id.fragmentContainer, this.fragment).commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (!getFragment().onBackPressed()) {
            super.onBackPressed();
        }
    }

    private void setupToolbar() {
        final Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        if (null == toolbar) {
            return;
        }
        setSupportActionBar(toolbar);
    }

    protected final String r(final int rID) {
        try {
            return getResources().getString(rID);
        }
        catch (Resources.NotFoundException e) {
            Log.e(e.getLocalizedMessage());
            return "";
        }
    }

    protected final String r(final int rID, final Object... format) {
        try {
            return getResources().getString(rID, format);
        }
        catch (Resources.NotFoundException e) {
            Log.e(e.getLocalizedMessage());
            return "";
        }
    }
}
