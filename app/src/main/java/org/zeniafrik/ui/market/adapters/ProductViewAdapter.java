package org.zeniafrik.ui.market.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.zeniafrik.R.layout;
import org.zeniafrik.models.BaseProduct;
import org.zeniafrik.ui.market.ProductDetailActivity;
import org.zeniafrik.ui.market.interfaces.OnItemClickListener;
import org.zeniafrik.ui.market.viewholders.ProductViewHolder;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

/**
 * Created by BraDev ${LOCALE} on 5/7/2018.
 */
public class ProductViewAdapter extends Adapter<ProductViewHolder> implements OnItemClickListener {
    private List<BaseProduct> productList = new ArrayList<>();
    private int viewType;

    public ProductViewAdapter(int viewType) {
        this.viewType = viewType;
    }

    public void setProductList(List productList) {
        if (!this.productList.isEmpty()) this.productList.clear();
        this.productList.addAll(productList);
        notifyDataSetChanged();
    }

    public void add_moreProducts(List productList){
        int count = getItemCount();
        this.productList.addAll(productList);
        notifyItemInserted(count);
    }

    @Override
    public int getItemViewType(int position) {
        return viewType;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater productLayout = LayoutInflater.from(parent.getContext());
        View customView;
        if (viewType == 1) {
            customView = productLayout.inflate(layout.similar_product_view, parent, false);
        } else {
            customView = productLayout.inflate(layout.minimal_product, parent, false);
        }
        return new ProductViewHolder(customView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        BaseProduct product = productList.get(position);
        try {
            holder.seller.setText(product.publisher);
            holder.product_price.setText(format("GHc %s", product.getPrice()));
            holder.product_name.setText(product.name);
            Uri imageUri = Uri.parse(product.image_url);
            holder.product_image.setImageURI(imageUri);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    @Override
    public void onClick(View view, int position) {
        BaseProduct product = productList.get(position);
        Context context = view.getContext();
        Intent intent = new Intent(context, ProductDetailActivity.class);
        intent.putExtra("org.zeniafrik.models.BaseProduct", product);
        context.startActivity(intent);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        productList.clear();
        productList = null;
    }

}
