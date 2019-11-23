package org.zeniafrik.di;

import org.zeniafrik.ui.market.AccountFragment;
import org.zeniafrik.ui.market.CategoryFragment;
import org.zeniafrik.ui.market.FeedFragment;
import org.zeniafrik.ui.market.MarketFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class HomeFragmentsBuilder {

    @ContributesAndroidInjector
    abstract AccountFragment contributeProfileFragment();

    @ContributesAndroidInjector
    abstract MarketFragment contributeMarketFragment();

    @ContributesAndroidInjector
    abstract FeedFragment contributeFeedFragment();

    @ContributesAndroidInjector
    abstract CategoryFragment contributeCategoryFragment();
}
