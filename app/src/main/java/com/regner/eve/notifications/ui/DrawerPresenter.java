package com.regner.eve.notifications.ui;

import android.R;

import javax.inject.Inject;

public class DrawerPresenter extends ViewPresenter<DrawerView> {

    private int selection = -1;

    @Inject
    public DrawerPresenter() {}

    public boolean onDrawerViewSelected(final int itemID) {
        if ( R.id.home == itemID) {
            getView().openDrawer();
            return true;
        }

        if (itemID == selection) {
            return true;
        }
/*
        switch (itemID) {
            case id.drawerFittings:
                this.selection = id.drawerFittings;
                getView().show(FittingListActivity.class);
                return true;

            case id.drawerDatabase:
                this.selection = id.drawerDatabase;
                getView().show(ItemListActivity.class);
                return true;

            case id.drawerSettings:
                this.selection = id.drawerSettings;
                getView().show(SettingsActivity.class);
                return true;

            case id.drawerLogin:
                return true;

            case id.drawerLogout:
                return true;

            default:
                return false;
        }*/
        return false;
    }

    public int getDrawerViewSelected() {
        return this.selection;
    }
}
