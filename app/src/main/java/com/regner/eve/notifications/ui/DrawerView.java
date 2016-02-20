package com.regner.eve.notifications.ui;

import android.app.Activity;

public interface DrawerView {

    void openDrawer();

    void closeDrawer();

    void show(final Class<? extends Activity> aClass);
}
