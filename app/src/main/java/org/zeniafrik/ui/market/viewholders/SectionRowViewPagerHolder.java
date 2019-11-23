package org.zeniafrik.ui.market.viewholders;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;

import org.zeniafrik.R.id;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SectionRowViewPagerHolder extends ViewHolder {

    @BindView(id.slider_holder)
    public ViewPager imageViewPager;

    @BindView(id.indicator)
    public TabLayout indicator;

    public SectionRowViewPagerHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}