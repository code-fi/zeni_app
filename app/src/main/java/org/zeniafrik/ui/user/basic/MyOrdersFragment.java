package org.zeniafrik.ui.user.basic;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.zeniafrik.R;
import org.zeniafrik.di.Injectable;
import org.zeniafrik.factory.ViewModelFactory;
import org.zeniafrik.helper.ViewLifecycleFragment;
import org.zeniafrik.models.Order;
import org.zeniafrik.ui.user.adapters.MyOrdersAdapter;
import org.zeniafrik.ui.user.viewmodels.MyOrdersViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static java.util.Objects.requireNonNull;

public class MyOrdersFragment extends ViewLifecycleFragment implements Injectable, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.productList)
    RecyclerView recyclerView;

    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.error_view)
    View error_view;

    @BindView(R.id.empty)
    View empty_view;


    @OnClick(R.id.retry_btn)
    public void retry() {
        onRefresh();
    }

    private Unbinder unbinder;
    private MyOrdersAdapter adapter;

    @Inject
    ViewModelFactory factory;

    @Inject
    MyOrdersViewModel viewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_products_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(MyOrdersFragment.this, factory).get(MyOrdersViewModel.class);
        viewModel.getOrders().observe(getViewLifecycleOwner(), orders_response -> {
            switch (requireNonNull(orders_response).status) {
                case ERROR:
                    error_occurred();
                    break;
                case SUCCESS:
                    loaded_ok(requireNonNull(orders_response.data).getData());
                    break;
            }
        });

        adapter = new MyOrdersAdapter();
        swipeRefreshLayout.setOnRefreshListener(MyOrdersFragment.this);
        recyclerView.setAdapter(adapter);
        Context context = requireNonNull(getContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new ItemOffsetDecoration(context, R.dimen.linear_minimal_outline));
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
        if (error_view.isShown()) error_view.setVisibility(View.GONE);
        viewModel.refresh();
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

    private void loaded_ok(ArrayList<Order> orders) {
        if (!orders.isEmpty()) {
            adapter.addProducts(orders);
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
}