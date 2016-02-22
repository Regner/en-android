package com.regner.eve.notifications.crest;


import android.net.Uri;

public interface CrestFacade {

    String start();

    CrestStatus login(final Uri authData);

    void logout();

}
