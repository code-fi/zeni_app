package org.zeniafrik.ui.user.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.zeniafrik.R;
import org.zeniafrik.models.ProductCategory;

public class CategoryListAdapter extends ArrayAdapter<ProductCategory> {

    public CategoryListAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.simple_product_category_item,parent,false);
        }
        ProductCategory category = getItem(position);
        TextView category_name = convertView.findViewById(R.id.category_item_name);
        category_name.setText(category.getName());

        return convertView;
    }


}
