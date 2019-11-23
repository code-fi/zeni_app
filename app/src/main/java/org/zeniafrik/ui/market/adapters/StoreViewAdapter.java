package org.zeniafrik.ui.market.adapters;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.zeniafrik.R.layout;
import org.zeniafrik.models.BaseShop;
import org.zeniafrik.ui.market.viewholders.StoreViewHolder;

import java.util.ArrayList;

/**
 * Created by BraDev ${LOCALE} on 5/7/2018.
 */
public class StoreViewAdapter extends RecyclerView.Adapter<StoreViewHolder> {
    private final ArrayList<BaseShop> shopList;
    private final int viewType;

    public StoreViewAdapter(ArrayList<BaseShop> shopList, int type) {
        this.shopList = shopList;
        viewType = type;
    }

    @Override
    public int getItemViewType(int position) {
        return viewType;
    }

    @NonNull

    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater productLayout = LayoutInflater.from(parent.getContext());
        View customView = productLayout.inflate(layout.shop_list_component_horizontal, parent, false);
//        if (viewType == 1){
//            customView = productLayout.inflate(layout.product_list_component_vert, parent, false);
//        }else {
//            customView = productLayout.inflate(layout.minimal_product,parent,false);
//        }
        return new StoreViewHolder(customView);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder holder, int position) {
        BaseShop product = shopList.get(position);
        try {
            holder.least_price.setText(product.getPrice());
            holder.store_name.setText(product.getName());
            Uri imageUri = Uri.parse(product.getImageUrl());
            holder.shop_image.setImageURI(imageUri);
//            if (viewType==0){
//            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }
}
