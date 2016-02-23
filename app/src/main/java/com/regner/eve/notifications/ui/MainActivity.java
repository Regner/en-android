package com.regner.eve.notifications.ui;

import android.os.Bundle;

import com.regner.eve.notifications.ApplicationComponent;
import com.regner.eve.notifications.R;

import butterknife.ButterKnife;

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

        setTitle(R.string.title_logged_out);
        setDescription(R.string.title_logged_out_description);

    }

}
