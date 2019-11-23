package org.zeniafrik.ui.user.adapters;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import org.zeniafrik.R;
import org.zeniafrik.ui.market.interfaces.OnItemClickListener;
import org.zeniafrik.ui.user.viewholders.ImageSelectionViewHolder;

import java.util.ArrayList;

public class ImageListAdapter extends RecyclerView.Adapter<ImageSelectionViewHolder> {
    private ArrayList<Uri> uris;
    private OnItemClickListener listener;

    public ImageListAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ImageSelectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater categoryItemInflater = LayoutInflater.from(parent.getContext());
        View categoryView = categoryItemInflater.inflate(R.layout.image_selection_list_item, parent, false);
        return new ImageSelectionViewHolder(categoryView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageSelectionViewHolder holder, int position) {

        if (uris != null) {
            Uri u = uris.get(position);
            if (u != null) {
                ImageRequest request = ImageRequestBuilder.newBuilderWithSource(u)
                        .setResizeOptions(new ResizeOptions(140, 140))
                        .build();
                holder.simpleDraweeView.setController(
                        Fresco.newDraweeControllerBuilder()
                                .setOldController(holder.simpleDraweeView.getController())
                                .setImageRequest(request)
                                .build());
            }
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public void setUris(ArrayList<Uri> uris) {
        this.uris = uris;
        notifyDataSetChanged();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ImageSelectionViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        listener = null;
    }
}
