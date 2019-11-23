package org.zeniafrik.ui.user.adapters;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.zeniafrik.R;
import org.zeniafrik.models.BaseProduct;
import org.zeniafrik.ui.market.interfaces.OnItemClickListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyProductsAdapter extends RecyclerView.Adapter<MyProductViewHolder> implements OnItemClickListener{

    private final ArrayList<BaseProduct>  products = new ArrayList<>();
    @NonNull
    @Override
    public MyProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater productItemInflater = LayoutInflater.from(parent.getContext());
        View productView = productItemInflater.inflate(R.layout.my_product_item_component,parent,false);
        return new MyProductViewHolder(productView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull MyProductViewHolder holder, int position) {
        BaseProduct product = products.get(position);
        try {
            String o = String.format("Ghc %s",product.getPrice());
            holder.product_image.setImageURI(Uri.parse(product.image_url));
            holder.product_name.setText(product.name);
            holder.product_price.setText(o);
            holder.product_status.setText(product.status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void addProducts(ArrayList<BaseProduct> data) {
        int count = getItemCount();
        if (count > 0) {
            products.clear();
        }
        products.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View view, int position) {

    }
}

class MyProductViewHolder extends RecyclerView.ViewHolder {


    @BindView(R.id.product_image)
    public SimpleDraweeView product_image;

    @BindView(R.id.product_name)
    public TextView product_name;

    @BindView(R.id.product_price)
    public TextView product_price;

    @BindView(R.id.product_status)
    public TextView product_status;


    @BindView(R.id.wrapper)
    View wrapper;

    public MyProductViewHolder(View itemView, OnItemClickListener listener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        wrapper.setOnClickListener(v -> listener.onClick(v, getAdapterPosition()));
    }
}