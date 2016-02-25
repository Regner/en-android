package com.regner.eve.notifications.ui;

import android.os.Bundle;
import android.view.Menu;

import com.regner.eve.notifications.ApplicationComponent;
import com.regner.eve.notifications.R;

public class MainActivity extends AbstractActivity<FeedListFragment> {

    @Override
    protected FeedListFragment createFragment() {
        return new FeedListFragment();
    }

    @Override
    protected void inject(ApplicationComponent component) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setTitle(R.string.app_name);
        setDescription(R.string.app_description);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }
}
