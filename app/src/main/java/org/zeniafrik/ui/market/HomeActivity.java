package org.zeniafrik.ui.market;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.zeniafrik.R;
import org.zeniafrik.R.id;
import org.zeniafrik.R.layout;
import org.zeniafrik.helper.BaseActivity;
import org.zeniafrik.helper.BottomNavigationHelper;
import org.zeniafrik.ui.market.adapters.MarketPagerAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class HomeActivity extends BaseActivity implements HasSupportFragmentInjector, OnPageChangeListener {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @BindView(id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    @BindView(id.main_pager)
    ViewPager mainPager;

    @BindView(id.toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_home);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        setSupportActionBar(toolbar);

        MarketPagerAdapter marketPagerAdapter = new MarketPagerAdapter(getSupportFragmentManager());

        mainPager.setAdapter(marketPagerAdapter);
        mainPager.setOffscreenPageLimit(3);
        BottomNavigationHelper.disableShiftMode(bottomNavigationView);

        mainPager.addOnPageChangeListener(this);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            int pagerPosition = mainPager.getCurrentItem();
            switch (id) {
                case R.id.action_main:
                    if (pagerPosition != 0) mainPager.setCurrentItem(0, false);
                    return true;
                case R.id.action_category:
                    if (pagerPosition != 1) mainPager.setCurrentItem(1, false);
                    return true;
                case R.id.action_account:
                    if (pagerPosition != 2) mainPager.setCurrentItem(2, false);
                    return true;
                case R.id.action_feed:
                    if (pagerPosition != 3) mainPager.setCurrentItem(3, false);
                    return true;
                default:
                    return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ds_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent searchIntent = new Intent(getApplicationContext(),SearchFlowActivity.class);
        startActivity(searchIntent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        mainPager.removeOnPageChangeListener(this);
        super.onDestroy();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                bottomNavigationView.setSelectedItemId(id.action_main);
                break;
            case 1:
                bottomNavigationView.setSelectedItemId(id.action_category);
                break;
            case 2:
                bottomNavigationView.setSelectedItemId(id.action_account);
                break;
            case 3:
                bottomNavigationView.setSelectedItemId(id.action_feed);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
