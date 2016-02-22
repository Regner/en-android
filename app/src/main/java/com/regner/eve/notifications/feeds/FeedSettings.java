package com.regner.eve.notifications.feeds;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FeedSettings {

    private String characterID;
    private Map<String, Boolean> settings = new HashMap<>();

    public String getCharacterID() {
        return characterID;
    }

    public void setCharacterID(String characterID) {
        this.characterID = characterID;
    }

    public Map<String, Boolean> getSettings() {
        return Collections.unmodifiableMap(settings);
    }

    public void setSettings(final String feed, final boolean enabled) {
        this.settings.put(feed, enabled);
    }

    public boolean getSettings(final String feed) {
        final Boolean b = this.settings.get(feed);
        return (null == b) ? false : b;
    }

    public void setSettings(Map<String, Boolean> settings) {
        this.settings.clear();
        if (null != settings) {
            this.settings.putAll(settings);
        }
    }
}
