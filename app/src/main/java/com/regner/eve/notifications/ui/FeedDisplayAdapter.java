package com.regner.eve.notifications.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.regner.eve.notifications.R;
import com.regner.eve.notifications.gcm.Message;

import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

final class FeedDisplayAdapter extends RecyclerView.Adapter<FeedDisplayAdapter.MessageHolder> {

    static class MessageHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.rowMessageTitle)
        TextView titleView;

        @Bind(R.id.rowMessageDescription)
        TextView descriptionView;

        public MessageHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void render(final Message message) {
            this.titleView.setText(message.getTitle());
            this.descriptionView.setText(message.getSubtitle());
        }
    }

    private final List<Message> messages = new LinkedList<>();

    public void addMessage(final Message message) {
        this.messages.add(message);
        notifyItemInserted(this.messages.size() - 1);
    }

    @Override
    public MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_feed, parent, false);
        return new MessageHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageHolder holder, int position) {
        final Message message = this.messages.get(position);
        holder.itemView.setOnClickListener(v -> onMessageSelected(message));
        holder.render(message);
    }

    @Override
    public int getItemCount() {
        return this.messages.size();
    }

    protected void onMessageSelected(final Message message) {

    }
}
