package org.zeniafrik.ui.user.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;

import org.zeniafrik.R;
import org.zeniafrik.ui.market.interfaces.OnItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageSelectionViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.selected_image_holder)
    public SimpleDraweeView simpleDraweeView;

    public ImageSelectionViewHolder(View itemView, OnItemClickListener listener) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        simpleDraweeView.setOnClickListener(v -> listener.onClick(v, getAdapterPosition()));
    }
}
