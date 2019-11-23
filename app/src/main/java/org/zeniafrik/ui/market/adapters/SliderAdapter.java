package org.zeniafrik.ui.market.adapters;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

import org.zeniafrik.R;
import org.zeniafrik.models.Banner;

import java.util.ArrayList;
import java.util.List;


public class SliderAdapter extends PagerAdapter {
    private final List<Banner> banners = new ArrayList<>();

    public void setBanners(List banners) {
        this.banners.clear();
        this.banners.addAll(banners);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return banners.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup view, int position) {
        LayoutInflater inflater = LayoutInflater.from(view.getContext());
        View myImageLayout = inflater.inflate(R.layout.slide_component, view, false);
        SimpleDraweeView myImage = myImageLayout.findViewById(R.id.slider_image);
        String image = banners.get(position).getImageUrl();
        myImage.setImageURI(Uri.parse(image));
        view.addView(myImageLayout, 0);
        return myImageLayout;
    }


}
