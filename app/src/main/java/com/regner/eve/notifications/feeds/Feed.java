package com.regner.eve.notifications.feeds;

public class Feed {

    private String url;
    private String name;

    private boolean official;

    public String getUrl() {
        return url;
    }

    public Feed setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getName() {
        return name;
    }

    public Feed setName(String name) {
        this.name = name;
        return this;
    }

    public boolean getOfficial() {
        return official;
    }

    public Feed setOfficial(boolean official) {
        this.official = official;
        return this;
    }
}
