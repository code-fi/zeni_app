package org.zeniafrik.ui.user.basic;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import org.zeniafrik.R;
import org.zeniafrik.factory.ViewModelFactory;
import org.zeniafrik.helper.BaseActivity;
import org.zeniafrik.helper.Status;
import org.zeniafrik.models.UserLocalObject;
import org.zeniafrik.ui.account.AccountActivity;
import org.zeniafrik.ui.user.PostProductActivity;
import org.zeniafrik.ui.user.viewmodels.DashboardViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

import static java.util.Objects.requireNonNull;

public class DashboardActivity extends BaseActivity implements HasSupportFragmentInjector{

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.name)
    TextView shop_name;

    @BindView(R.id.membership)
    TextView membership;

    @BindView(R.id.location)
    TextView location;

    @BindView(R.id.viewpager)
    ViewPager pager;

    @BindView(R.id.tablayout)
    TabLayout tabLayout;

    @OnClick(R.id.edit)
    public void editProfile(){

    }

    @OnClick(R.id.add_product)
    public void addProduct(){
        Intent intent = new Intent().setClass(DashboardActivity.this, PostProductActivity.class);
        startActivity(intent);
    }
//    @OnClick(R.id.upgrade_premium)
//    public void upgradePremium(){
//
//    }

//
//    @OnClick(R.id.products_btn)
//    public void viewProducts(){

//    }

    @Inject
    DashboardViewModel viewModel;

    @Inject
    ViewModelFactory factory;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_user_dashboard);
        unbinder = ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pager.setAdapter(new DashboardTabAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(pager);
        viewModel = ViewModelProviders.of(this,factory).get(DashboardViewModel.class);

        viewModel.getUserLocalObject().observe(this,userObject->{
            if (requireNonNull(userObject).status == Status.SUCCESS){
                UserLocalObject localObject = userObject.data;
                if (localObject != null){
                    shop_name.setText(localObject.name);
                    location.setText(localObject.phone);
                }else showAuthentication();
            }
        });
    }

    private void showAuthentication() {
        viewModel.logout();
        finish();
        Intent intent = new Intent();
        intent.setClass(this, AccountActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
}
