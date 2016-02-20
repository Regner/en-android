package com.regner.eve.notifications.ui;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.regner.eve.notifications.Application;
import com.regner.eve.notifications.ApplicationComponent;
import com.regner.eve.notifications.R;
import com.regner.eve.notifications.util.Log;

public abstract class AbstractActivity extends AppCompatActivity implements TitleView {

    protected abstract void inject(final ApplicationComponent component);

    @Override
    public final void setLoading(boolean show) {
        final ProgressBar pb = (ProgressBar)findViewById(R.id.toolbarProgress);
        if (null == pb) {
            return;
        }
        pb.setVisibility((show) ? View.VISIBLE : View.GONE);
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
        final ApplicationComponent appComponent = ((Application)getApplication()).getAppComponent();
        inject(appComponent);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setupToolbar();
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
