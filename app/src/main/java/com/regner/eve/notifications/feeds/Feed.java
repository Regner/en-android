package com.regner.eve.notifications.feeds;

public class Feed {

    private String url;
    private String name;

    private boolean official;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getOfficial() {
        return official;
    }

    public void setOfficial(boolean official) {
        this.official = official;
    }
}
