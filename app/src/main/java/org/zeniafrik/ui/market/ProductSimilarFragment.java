package org.zeniafrik.ui.market;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.zeniafrik.R;
import org.zeniafrik.di.Injectable;
import org.zeniafrik.factory.ViewModelFactory;
import org.zeniafrik.helper.Status;
import org.zeniafrik.helper.ViewLifecycleFragment;
import org.zeniafrik.models.BaseProduct;
import org.zeniafrik.ui.market.ViewModels.ProductDetailViewModel;
import org.zeniafrik.ui.market.adapters.ProductViewAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static java.util.Objects.requireNonNull;

public class ProductSimilarFragment extends ViewLifecycleFragment implements Injectable {
    @BindView(R.id.productList)
    RecyclerView productList;
    @BindView(R.id.loading)
    LinearLayout loading;
    @BindView(R.id.des)
    LinearLayout des;
    @Inject
    ViewModelFactory factory;
    @Inject
    ProductDetailViewModel viewModel;
    private Unbinder unbinder;
    private ProductViewAdapter adapter;


    public ProductSimilarFragment() {
        adapter = new ProductViewAdapter(1);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.product_similar_component, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(ProductSimilarFragment.this, view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity(), factory).get(ProductDetailViewModel.class);

        viewModel.getSimilarProducts().observe(getViewLifecycleOwner(),
                similar_product_response -> {
                    if (requireNonNull(similar_product_response).status == Status.SUCCESS) {
                        loading.setVisibility(View.GONE);
                        ArrayList<BaseProduct> similar_products = requireNonNull(similar_product_response.data).getData();
                        if (similar_products.size() > 0) adapter.setProductList(similar_products);
                        else des.setVisibility(View.GONE);
                    }
                });
        productList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        productList.setAdapter(adapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter = null;
    }
}
