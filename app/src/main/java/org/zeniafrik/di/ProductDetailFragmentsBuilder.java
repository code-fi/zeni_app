package org.zeniafrik.di;

import org.zeniafrik.ui.market.ProductSimilarFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ProductDetailFragmentsBuilder {

    @ContributesAndroidInjector
    abstract ProductSimilarFragment productSimilarFragment();

}
