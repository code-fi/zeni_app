package org.zeniafrik.di;

/**
 * Created by BraDev ${LOCALE} on 1/12/2018.
 */

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import org.zeniafrik.factory.ViewModelFactory;
import org.zeniafrik.ui.account.ViewModels.LoginViewModel;
import org.zeniafrik.ui.account.ViewModels.RegistrationViewModel;
import org.zeniafrik.ui.market.ViewModels.AccountFragmentViewModel;
import org.zeniafrik.ui.market.ViewModels.CategoryFragmentViewModel;
import org.zeniafrik.ui.market.ViewModels.FeedFragmentViewModel;
import org.zeniafrik.ui.market.ViewModels.MarketFragmentViewModel;
import org.zeniafrik.ui.market.ViewModels.OrderPlacementViewModel;
import org.zeniafrik.ui.market.ViewModels.ProductDetailViewModel;
import org.zeniafrik.ui.market.ViewModels.ProductListActivityViewModel;
import org.zeniafrik.ui.market.ViewModels.SearchFlowViewModel;
import org.zeniafrik.ui.user.viewmodels.DashboardViewModel;
import org.zeniafrik.ui.user.viewmodels.MyOrdersViewModel;
import org.zeniafrik.ui.user.viewmodels.MyProductsViewModel;
import org.zeniafrik.ui.user.viewmodels.PostSharedViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelBuilder {

    @Binds
    @IntoMap
    @ViewModelKey(MarketFragmentViewModel.class)
    abstract ViewModel MarketFragmentVM(MarketFragmentViewModel marketFragmentViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    public abstract ViewModel LoginViewModel(LoginViewModel loginViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ProductDetailViewModel.class)
    public abstract ViewModel ProductDetailViewModel(ProductDetailViewModel productDetailViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(CategoryFragmentViewModel.class)
    public abstract ViewModel CategoryFragmentViewModel(CategoryFragmentViewModel categoryFragmentViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(FeedFragmentViewModel.class)
    public abstract ViewModel FeedFragmentViewModel(FeedFragmentViewModel feedFragmentViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ProductListActivityViewModel.class)
    public abstract ViewModel ProductListActivityViewModel(ProductListActivityViewModel productListActivityViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(RegistrationViewModel.class)
    public abstract ViewModel RegistrationViewModel(RegistrationViewModel registrationViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AccountFragmentViewModel.class)
    public abstract ViewModel AccountFragmentViewModel(AccountFragmentViewModel accountFragmentViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PostSharedViewModel.class)
    public abstract ViewModel PostSharedViewModel(PostSharedViewModel postSharedViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DashboardViewModel.class)
    public abstract ViewModel DashboardViewModel(DashboardViewModel dashboardViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MyProductsViewModel.class)
    public abstract ViewModel MyProductsViewModel(MyProductsViewModel myProductsViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MyOrdersViewModel.class)
    public abstract ViewModel MyOrdersViewModel(MyOrdersViewModel myOrdersViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(OrderPlacementViewModel.class)
    public abstract ViewModel OrderPlacementViewModel(OrderPlacementViewModel orderPlacementViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SearchFlowViewModel.class)
    public abstract ViewModel SearchFlowViewModel(SearchFlowViewModel searchFlowViewModel);

    @Binds
    abstract ViewModelProvider.Factory factory(ViewModelFactory factory);

}