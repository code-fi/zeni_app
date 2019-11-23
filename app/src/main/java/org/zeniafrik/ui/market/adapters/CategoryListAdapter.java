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
import org.zeniafrik.models.ProductCategory;
import org.zeniafrik.ui.market.ProductListActivity;
import org.zeniafrik.ui.market.interfaces.OnItemClickListener;
import org.zeniafrik.ui.market.viewholders.CategoryViewHolder;

import java.util.ArrayList;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryViewHolder> implements OnItemClickListener {
    private final ArrayList<ProductCategory> categories = new ArrayList<>();


    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater categoryItemInflater = LayoutInflater.from(parent.getContext());
        View categoryView = categoryItemInflater.inflate(R.layout.category_item_component, parent, false);
        return new CategoryViewHolder(categoryView, CategoryListAdapter.this);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        ProductCategory category = categories.get(position);
        try {
            holder.category_item_image.setImageURI(Uri.parse(category.getImage_url()));
            holder.category_item_name.setText(category.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }


    public void addCategories(ArrayList<ProductCategory> data) {
        int count = getItemCount();
        if (count > 0) {
            categories.clear();
        }
        categories.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View view, int position) {
        ProductCategory category = categories.get(position);
        Context context = view.getContext();
        Intent intent = new Intent(context, ProductListActivity.class);
        intent.putExtra("org.zeniafrik.models.ProductCategory", category);
        context.startActivity(intent);
    }
}
