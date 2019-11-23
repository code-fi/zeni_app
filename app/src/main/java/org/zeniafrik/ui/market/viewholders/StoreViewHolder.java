package org.zeniafrik.ui.market.viewholders;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.zeniafrik.R.id;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by BraDev ${LOCALE} on 5/7/2018.
 */
public class StoreViewHolder extends ViewHolder {

    @BindView(id.shop_name)
    public TextView store_name;

    @BindView(id.shop_image)
    public SimpleDraweeView shop_image;

    @BindView(id.least_price)
    public TextView least_price;


    public StoreViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
