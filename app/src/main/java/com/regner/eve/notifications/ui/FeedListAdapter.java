package com.regner.eve.notifications.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.regner.eve.notifications.R;
import com.regner.eve.notifications.feeds.Feed;
import com.regner.eve.notifications.feeds.FeedList;
import com.regner.eve.notifications.feeds.FeedSettings;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

final class FeedListAdapter extends RecyclerView.Adapter<FeedListAdapter.FeedHolder> {

    static class FeedHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.rowFeedName)
        TextView feedName;

        @Bind(R.id.rowFeedURL)
        TextView feedURL;

        public FeedHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void render(final Feed feed, final boolean enabled) {
            this.feedName.setText(feed.getName());
            this.feedURL.setText(feed.getUrl());
        }
    }

    private final List<Feed> feeds = new LinkedList<>();
    private final Map<String, Boolean> settings = new HashMap<>();

    public void setFeeds(final FeedList feeds) {
        this.feeds.clear();
        this.settings.clear();

        if (null != feeds) {
            this.feeds.addAll(feeds.getFeeds().values());
        }
        notifyDataSetChanged();
    }

    public void setSettings(final FeedSettings settings) {
        this.settings.clear();
        if (null != settings) {
            this.settings.putAll(settings.getSettings());
        }
        notifyDataSetChanged();
    }

    @Override
    public FeedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_feed, parent, false);
        return new FeedHolder(view);
    }

    @Override
    public void onBindViewHolder(FeedHolder holder, int position) {
        final Feed feed = this.feeds.get(position);
        final Boolean enabled = this.settings.get(feed.getName());
        holder.itemView.setOnClickListener(v -> onFeedSelected(feed));
        holder.render(feed, (null == enabled) ? false : enabled.booleanValue());
    }

    @Override
    public int getItemCount() {
        return this.feeds.size();
    }

    protected void onFeedSelected(final Feed feed) {}
}
