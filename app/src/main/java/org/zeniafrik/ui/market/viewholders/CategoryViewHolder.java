package org.zeniafrik.ui.market.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.zeniafrik.R;
import org.zeniafrik.ui.market.interfaces.OnItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryViewHolder extends RecyclerView.ViewHolder {


    @BindView(R.id.category_item_image)
    public SimpleDraweeView category_item_image;
    @BindView(R.id.category_item_name)
    public TextView category_item_name;
    @BindView(R.id.wrapper)
    View wrapper;

    public CategoryViewHolder(View itemView, OnItemClickListener listener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        wrapper.setOnClickListener(v -> listener.onClick(v, getAdapterPosition()));
    }
}
