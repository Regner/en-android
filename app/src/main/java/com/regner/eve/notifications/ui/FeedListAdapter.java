package com.regner.eve.notifications.ui;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.regner.eve.notifications.R;
import com.regner.eve.notifications.feeds.Feed;
import com.regner.eve.notifications.feeds.FeedList;

import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

class FeedListAdapter extends RecyclerView.Adapter<FeedListAdapter.FeedHolder> {

    static class FeedHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.rowFeedName)
        TextView feedName;

        @Bind(R.id.rowFeedURL)
        TextView feedURL;

        public FeedHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void render(final Feed feed) {
            this.feedName.setText(feed.getName());
            this.feedName.setTextColor(feed.getEnabled() ? Color.GREEN : Color.DKGRAY);
            this.feedURL.setText(feed.getUrl());
        }
    }

    private final List<Feed> feeds = new LinkedList<>();

    public void setFeeds(final FeedList feeds) {
        this.feeds.clear();
        if (null != feeds) {
            this.feeds.addAll(feeds.getFeeds().values());
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
        holder.itemView.setOnClickListener(v -> onFeedSelected(feed, position));
        holder.render(feed);
    }

    @Override
    public int getItemCount() {
        return this.feeds.size();
    }

    protected void onFeedSelected(final Feed feed, final int position) {}
}
