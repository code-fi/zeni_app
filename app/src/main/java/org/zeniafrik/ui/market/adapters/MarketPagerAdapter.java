package org.zeniafrik.ui.market.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.zeniafrik.ui.market.AccountFragment;
import org.zeniafrik.ui.market.CategoryFragment;
import org.zeniafrik.ui.market.FeedFragment;
import org.zeniafrik.ui.market.MarketFragment;


public class MarketPagerAdapter extends FragmentPagerAdapter {

    public MarketPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MarketFragment();
            case 1:
                return new CategoryFragment();
            case 2:
                return new AccountFragment();
            case 3:
                return new FeedFragment();

        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

}
