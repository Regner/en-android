package com.regner.eve.notifications.ui;

import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.MenuItem;

public final class Activities {

	private Activities() {}


	public static MenuItem enableMenuItem(int menuItemId, Menu menu) {
        return enableMenuItem(menuItemId, menu, true, true);
    }

    public static MenuItem enableMenuItem(int menuItemId, Menu menu, boolean enabled) {
        return enableMenuItem(menuItemId, menu, enabled, true);
    }

    public static MenuItem enableMenuItem(int menuItemId, Menu menu, boolean enabled, boolean visible) {
        MenuItem item = menu.findItem(menuItemId);
        if (null == item) {
            return null;
        }
        item.setVisible(visible);
        item.setEnabled(enabled);
        //Icons won't grey out by themselves.
        Drawable d = item.getIcon();
        if (null != d) {
            if (enabled) {
                d.clearColorFilter();
            }
            else {
                d.setColorFilter(Color.GRAY, Mode.MULTIPLY);
            }
        }
        return item;
    }
}
