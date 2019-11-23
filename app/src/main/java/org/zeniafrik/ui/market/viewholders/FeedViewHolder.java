package org.zeniafrik.ui.market.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.zeniafrik.R;
import org.zeniafrik.ui.market.interfaces.OnItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedViewHolder extends RecyclerView.ViewHolder {


    @BindView(R.id.feed_image)
    public SimpleDraweeView feed_image;
    @BindView(R.id.feed_category)
    public TextView feed_category;
    @BindView(R.id.feed_time)
    public TextView feed_time;
    @BindView(R.id.feed_title)
    public TextView feed_title;
    @BindView(R.id.feed)
    public View wrapper;

    public FeedViewHolder(View itemView, OnItemClickListener listener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        wrapper.setOnClickListener(v -> listener.onClick(v, getAdapterPosition()));
    }
}
