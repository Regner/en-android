package com.regner.eve.notifications.ui;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.regner.eve.notifications.R;
import com.regner.eve.notifications.feeds.Feed;
import com.regner.eve.notifications.gcm.Message;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

class FeedListAdapter extends RecyclerView.Adapter<FeedListAdapter.FeedHolder> {

    static class FeedHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.rowFeedName)
        TextView feedName;

        @Bind(R.id.rowFeedURL)
        TextView feedURL;

        @Bind(R.id.rowFeedMessage)
        TextView feedMessage;

        public FeedHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void render(final Feed feed, final String lastMessage) {
            this.feedName.setText(feed.getName());
            this.feedName.setTextColor(feed.getEnabled() ? Color.GREEN : Color.DKGRAY);
            this.feedURL.setText(feed.getUrl());
            if (StringUtils.isBlank(lastMessage)) {
                this.feedMessage.setVisibility(View.INVISIBLE);
            }
            else {
                this.feedMessage.setText(lastMessage);
                this.feedMessage.setVisibility(View.VISIBLE);
            }
        }
    }

    private final List<Feed> feeds = new LinkedList<>();
    private final Map<String, String> lastMessage = new HashMap<>();

    public void setFeeds(final List<Feed> feeds) {
        this.feeds.clear();
        this.lastMessage.clear();

        if (null != feeds) {
            this.feeds.addAll(feeds);
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
        holder.render(feed, this.lastMessage.get(feed.getTopic()));
    }

    @Override
    public int getItemCount() {
        return this.feeds.size();
    }

    public void setMessage(final Feed feed, final Message message) {
        this.lastMessage.put(feed.getTopic(), message.getTitle());
        notifyItemChanged(indexOf(feed));
    }

    protected void onFeedSelected(final Feed feed, final int position) {}

    private int indexOf(final Feed feed) {
        int index = 0;
        for (Feed f: this.feeds) {
            if (f.getTopic().equals(feed.getTopic())) {
                return index;
            }
            index = index + 1;
        }
        return -1;
    }
}
