package org.zeniafrik.ui.market.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.zeniafrik.R;
import org.zeniafrik.models.Feed;
import org.zeniafrik.ui.market.WebViewActivity;
import org.zeniafrik.ui.market.interfaces.OnItemClickListener;
import org.zeniafrik.ui.market.viewholders.FeedViewHolder;

import java.util.ArrayList;

public class FeedListAdapter extends RecyclerView.Adapter<FeedViewHolder> implements OnItemClickListener {
    private ArrayList<Feed> feeds = new ArrayList<>();


    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater categoryItemInflater = LayoutInflater.from(parent.getContext());
        View feedView = categoryItemInflater.inflate(R.layout.feed_article_component, parent, false);
        return new FeedViewHolder(feedView, FeedListAdapter.this);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {
        Feed feed = feeds.get(position);
        try {
            holder.feed_image.setImageURI(Uri.parse(feed.getImage_url()));
            holder.feed_category.setText(feed.getCategory());
            holder.feed_title.setText(feed.getTitle());
            holder.feed_time.setText(feed.getPublished_on());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return feeds.size();
    }


    public void addFeeds(ArrayList<Feed> data) {
        if(!feeds.isEmpty())feeds.clear();
        this.feeds.addAll(data);
        notifyDataSetChanged();
    }

    public void add_moreFeeds(ArrayList<Feed> data){
        int count = getItemCount();
        feeds.addAll(data);
        notifyItemInserted(count);
    }

    @Override
    public void onClick(View view, int position) {
        Feed feed = feeds.get(position);
        Context context = view.getContext();
        String[] a = {feed.getTitle(),feed.getUrl()};
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("a",a);
        context.startActivity(intent);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        feeds.clear();
        feeds = null;
    }

}
