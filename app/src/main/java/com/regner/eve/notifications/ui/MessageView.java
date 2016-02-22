package com.regner.eve.notifications.ui;

import com.regner.eve.notifications.gcm.Message;

public interface MessageView {

    void onMessage(final Message message);
}
