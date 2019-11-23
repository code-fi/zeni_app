package org.zeniafrik.ui.market.viewholders;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.zeniafrik.R.id;
import org.zeniafrik.ui.market.interfaces.OnItemClickListener;

import javax.annotation.Nonnull;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by BraDev ${LOCALE} on 5/7/2018.
 */
public class ProductViewHolder extends ViewHolder {

    @BindView(id.product_name)
    public TextView product_name;
    @BindView(id.product_image)
    public SimpleDraweeView product_image;
    @BindView(id.product_price)
    public TextView product_price;
    @BindView(id.seller)
    public TextView seller;
    @BindView(id.product_wrapper)
    View product_wrapper;

    public ProductViewHolder(View itemView, @Nonnull OnItemClickListener listener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        product_wrapper.setOnClickListener(v -> listener.onClick(v, getAdapterPosition()));
    }

}
