package org.zeniafrik.ui.market;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.zeniafrik.R;
import org.zeniafrik.R.layout;
import org.zeniafrik.di.Injectable;
import org.zeniafrik.factory.ViewModelFactory;
import org.zeniafrik.helper.ItemOffsetDecoration;
import org.zeniafrik.helper.ViewLifecycleFragment;
import org.zeniafrik.models.ProductCategory;
import org.zeniafrik.ui.market.ViewModels.CategoryFragmentViewModel;
import org.zeniafrik.ui.market.adapters.CategoryListAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static java.util.Objects.requireNonNull;

/**
 * Created by BraDev ${LOCALE} on 5/2/2018.
 */
public class CategoryFragment extends ViewLifecycleFragment implements Injectable, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.productList)
    RecyclerView recyclerView;

    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.error_view)
    View error_view;

    @BindView(R.id.empty)
    View empty_view;

    @Inject
    ViewModelFactory factory;
    @Inject
    CategoryFragmentViewModel viewModel;
    private Unbinder unbinder;
    private CategoryListAdapter adapter;

    @OnClick(R.id.retry_btn)
    public void retry() {
        onRefresh();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        adapter = new CategoryListAdapter();

        viewModel = ViewModelProviders.of(CategoryFragment.this, factory).get(CategoryFragmentViewModel.class);
        viewModel.getCategories().observe(getViewLifecycleOwner(), category_response -> {
            switch (requireNonNull(category_response).status) {
                case SUCCESS:
                    loaded_ok(requireNonNull(category_response.data).getData());
                    break;
                case ERROR:
                    error_occurred();
                    break;

            }
        });

        swipeRefreshLayout.setOnRefreshListener(CategoryFragment.this);
        recyclerView.setAdapter(adapter);
        @NonNull
        Context context = requireNonNull(getContext());
        recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        recyclerView.addItemDecoration(new ItemOffsetDecoration(context, R.dimen.itemoffset));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint() && adapter.getItemCount() < 1) {
            onRefresh();
        }
    }

    private void error_occurred() {
        swipeRefreshLayout.setRefreshing(false);
        if (!error_view.isShown()) error_view.setVisibility(View.VISIBLE);
        if (empty_view.isShown()) empty_view.setVisibility(View.GONE);
        if (recyclerView.isShown()) recyclerView.setVisibility(View.GONE);
    }

    private void loaded_ok(ArrayList<ProductCategory> products) {
        if (!products.isEmpty()) {
            adapter.addCategories(products);
            if (!recyclerView.isShown()) recyclerView.setVisibility(View.VISIBLE);
            if (empty_view.isShown()) empty_view.setVisibility(View.GONE);
        } else {
            if (!empty_view.isShown()) empty_view.setVisibility(View.VISIBLE);
            if (recyclerView.isShown()) recyclerView.setVisibility(View.GONE);
        }
        if (error_view.isShown()) error_view.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isResumed() && isVisibleToUser) {
            onResume();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(layout.category_fragment, container, false);
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
        adapter = null;
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        if(error_view.isShown())error_view.setVisibility(View.GONE);
        viewModel.refresh();
    }
}
