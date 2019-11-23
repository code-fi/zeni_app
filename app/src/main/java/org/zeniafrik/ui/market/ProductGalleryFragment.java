package org.zeniafrik.ui.market;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.zeniafrik.R;
import org.zeniafrik.ui.market.adapters.ProductSliderAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ProductGalleryFragment extends Fragment implements ViewPager.OnPageChangeListener {

    @BindView(R.id.slider_holder)
    ViewPager slider_holder;
    @BindView(R.id.paging_wrapper)
    View paging_wrapper;
    @BindView(R.id.paging)
    TextView paging;

    private int page_size;
    private ProductSliderAdapter sliderAdapter;
    private Unbinder unbinder;

    public ProductGalleryFragment() {

    }

    public void addBaseImage(String imageUrl) {
        sliderAdapter.init(imageUrl);
        page_size = sliderAdapter.getCount();
        paging.setText(String.format("%1$s/%2$s", 1, page_size));
    }

    public void addGallery(ArrayList<String> images) {
        sliderAdapter.setMultiUrls(images);
        page_size = sliderAdapter.getCount();
        paging.setText(String.format("%1$s/%2$s", 1, page_size));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.image_slider_component, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        paging_wrapper.setVisibility(View.VISIBLE);
        sliderAdapter = new ProductSliderAdapter();
        slider_holder.setAdapter(sliderAdapter);
        slider_holder.addOnPageChangeListener(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        slider_holder.removeOnPageChangeListener(this);
        sliderAdapter = null;
        unbinder.unbind();
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        paging.setText(String.format("%1$s/%2$s", position + 1, page_size));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


}
