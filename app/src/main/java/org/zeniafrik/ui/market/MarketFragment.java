package org.zeniafrik.ui.market;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.zeniafrik.R.id;
import org.zeniafrik.R.layout;
import org.zeniafrik.di.Injectable;
import org.zeniafrik.helper.ViewLifecycleFragment;
import org.zeniafrik.models.BaseMarketComponents;
import org.zeniafrik.models.BaseProduct;
import org.zeniafrik.models.MarketSectionModel;
import org.zeniafrik.ui.market.ViewModels.MarketFragmentViewModel;
import org.zeniafrik.ui.market.adapters.MarketListAdapter;

import javax.annotation.Nullable;
import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.view.View.VISIBLE;
import static java.util.Objects.requireNonNull;
import static org.zeniafrik.models.MarketSectionModel.G_LAYOUT;
import static org.zeniafrik.models.MarketSectionModel.V_LAYOUT;

/**
 * Created by BraDev ${LOCALE} on 5/2/2018.
 */
public class MarketFragment extends ViewLifecycleFragment implements Injectable, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    ViewModelProvider.Factory factory;
    @Inject
    MarketFragmentViewModel marketFragmentViewModel;

    @BindView(id.productList)
    RecyclerView marketSectionList;

    @BindView(id.swipeLayout)
    SwipeRefreshLayout refreshLayout;

    @BindView(id.error_view)
    LinearLayout errorView;
    private Unbinder unbinder;
    private MarketListAdapter adapter;

    @OnClick(id.retry_btn)
    public void retry() {
        onRefresh();
    }

    @Override
    public void onViewCreated(@NonNull View view, @android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        adapter = new MarketListAdapter();
        marketFragmentViewModel = ViewModelProviders.of(this, factory).get(MarketFragmentViewModel.class);


        marketFragmentViewModel.getMarketComponents().observe(getViewLifecycleOwner(), extProductResponseResource -> {
            switch (requireNonNull(extProductResponseResource).status) {
                case SUCCESS:
                    BaseMarketComponents marketComponents = requireNonNull(extProductResponseResource.data).getData();
                    adapter.AddSection(new MarketSectionModel<>(null, 2, marketComponents.getBanners(), null));

                    for (BaseProduct.ProductListing aProductList : marketComponents.getProductListings()) {
                        if (!aProductList.data.isEmpty()) {
                            String layout = aProductList.title.equals("Fresh") ? G_LAYOUT : V_LAYOUT;
                            adapter.AddSection(new MarketSectionModel<>(aProductList.title, 1,
                                    aProductList.data, layout));
                        }
                    }

                    refreshLayout.setRefreshing(false);
                    break;
                case ERROR:
                    if (!errorView.isShown()) errorView.setVisibility(VISIBLE);
                    refreshLayout.setRefreshing(false);
                    break;
            }
        });

        refreshLayout.setOnRefreshListener(MarketFragment.this);

        marketSectionList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager
                .VERTICAL, false));
        marketSectionList.setAdapter(adapter);
        marketSectionList.setItemViewCacheSize(20);
        marketSectionList.setDrawingCacheEnabled(true);
        marketSectionList.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(layout.market_fragment, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint() && adapter.getItemCount() < 1) {
            onRefresh();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isResumed() && isVisibleToUser) {
            onResume();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter.destroy();
        adapter = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        if (errorView.isShown()) errorView.setVisibility(View.GONE);
        marketFragmentViewModel.setRefresh();
    }
}
