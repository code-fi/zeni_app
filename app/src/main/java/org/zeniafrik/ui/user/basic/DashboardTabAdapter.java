package org.zeniafrik.ui.user.basic;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class DashboardTabAdapter extends FragmentPagerAdapter {

    public DashboardTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) return new MyProductsFragment();
        return new MyOrdersFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return position == 0 ? "Posted Ads" : "Orders";
    }

}
