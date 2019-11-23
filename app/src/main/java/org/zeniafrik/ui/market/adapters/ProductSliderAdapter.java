package org.zeniafrik.ui.market.adapters;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

import org.zeniafrik.R;

import java.util.ArrayList;


public class ProductSliderAdapter extends PagerAdapter {
    private final ArrayList<String> banners = new ArrayList<>();

    @Override
    public int getCount() {
        return banners.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup view, int position) {
        LayoutInflater inflater = LayoutInflater.from(view.getContext());

        View myImageLayout = inflater.inflate(R.layout.slide_component, view, false);
        SimpleDraweeView myImage = myImageLayout.findViewById(R.id.slider_image);
        String image = banners.get(position);
        myImage.setImageURI(Uri.parse(image));
        view.addView(myImageLayout, 0);
        return myImageLayout;
    }


    public void init(String imageUrl) {
        this.banners.add(imageUrl);
        this.notifyDataSetChanged();
    }

    public void setMultiUrls(ArrayList<String> urls) {
        this.banners.addAll(urls);
        this.notifyDataSetChanged();
    }


}
