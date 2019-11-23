package org.zeniafrik.ui.user.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.zeniafrik.R;
import org.zeniafrik.models.Order;
import org.zeniafrik.ui.market.interfaces.OnItemClickListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrderViewHolder> implements OnItemClickListener {
    private final ArrayList<Order> orders = new ArrayList<>();
    @NonNull
    @Override
    public MyOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater productItemInflater = LayoutInflater.from(parent.getContext());
        View productView = productItemInflater.inflate(R.layout.my_order_item_component,parent,false);
        return new MyOrderViewHolder(productView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderViewHolder holder, int position) {
        Order order = orders.get(position);
        try {
            holder.product_name.setText(order.product_name);
            holder.order_time.setText(order.placed_on);
            holder.order_total.setText(String.format("GHc %s",order.getAmount()));
            switch (order.status){
                case "pending":
                    holder.status_ic.setImageResource(R.drawable.ic_status_pending);
                    break;
                case "delivered":
                    holder.status_ic.setImageResource(R.drawable.ic_status_delivered);
                    break;
                case "processing":
                    holder.status_ic.setImageResource(R.drawable.ic_status_processing);
                    break;
                case "delivering":
                    holder.status_ic.setImageResource(R.drawable.ic_status_delivering);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public void addProducts(ArrayList<Order> data) {
        int count = getItemCount();
        if (count > 0) {
            orders.clear();
        }
        orders.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View view, int position) {

    }
}

class MyOrderViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.product_name)
    public TextView product_name;

    @BindView(R.id.status_ic)
    public ImageView status_ic;

    @BindView(R.id.order_time)
    public TextView order_time;

    @BindView(R.id.order_total)
    public TextView order_total;


    @BindView(R.id.wrapper)
    View wrapper;

    public MyOrderViewHolder(View itemView, OnItemClickListener listener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        wrapper.setOnClickListener(v -> listener.onClick(v, getAdapterPosition()));
    }
}