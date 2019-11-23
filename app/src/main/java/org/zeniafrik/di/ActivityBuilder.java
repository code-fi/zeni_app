package org.zeniafrik.di;

import org.zeniafrik.ui.account.AccountActivity;
import org.zeniafrik.ui.market.HomeActivity;
import org.zeniafrik.ui.market.OrderPlacementActivity;
import org.zeniafrik.ui.market.ProductDetailActivity;
import org.zeniafrik.ui.market.ProductListActivity;
import org.zeniafrik.ui.market.SearchFlowActivity;
import org.zeniafrik.ui.user.AdDetailActivity;
import org.zeniafrik.ui.user.PostProductActivity;
import org.zeniafrik.ui.user.basic.DashboardActivity;
import org.zeniafrik.ui.user.basic.MyOrdersFragment;
import org.zeniafrik.ui.user.basic.MyProductsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by BraDev ${LOCALE} on 1/12/2018.
 */
@Module
public abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = HomeFragmentsBuilder.class)
    abstract HomeActivity homeActivity();

//    @ContributesAndroidInjector
//    abstract MarketFragment contributeMarketFragment();

    @ContributesAndroidInjector(modules = AccountFragmentBuilder.class)
    abstract AccountActivity accountActivity();

    //
    @ContributesAndroidInjector(modules = ProductDetailFragmentsBuilder.class)
    abstract ProductDetailActivity productDetail();

    @ContributesAndroidInjector()
    abstract ProductListActivity productListActivity();



    @ContributesAndroidInjector()
    abstract PostProductActivity postProductActivity();


    @ContributesAndroidInjector()
    abstract AdDetailActivity adDetailActivity();

    @ContributesAndroidInjector()
    abstract OrderPlacementActivity orderPlacementActivity();

    @ContributesAndroidInjector(modules = DashboardFragmentBuilder.class)
    abstract DashboardActivity dashboardActivity();
//
    @ContributesAndroidInjector
    abstract SearchFlowActivity searchFlowActivity();

}

@Module
abstract class DashboardFragmentBuilder {

    @ContributesAndroidInjector
    abstract MyProductsFragment myProductsFragment();

    @ContributesAndroidInjector
    abstract MyOrdersFragment myOrdersFragment();

}


