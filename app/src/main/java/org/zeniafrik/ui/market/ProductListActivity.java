package org.zeniafrik.ui.market;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import org.zeniafrik.R;
import org.zeniafrik.api.meta;
import org.zeniafrik.di.Injectable;
import org.zeniafrik.factory.ViewModelFactory;
import org.zeniafrik.helper.BaseActivity;
import org.zeniafrik.helper.ItemOffsetDecoration;
import org.zeniafrik.models.BaseProduct;
import org.zeniafrik.models.ProductCategory;
import org.zeniafrik.ui.market.ViewModels.ProductListActivityViewModel;
import org.zeniafrik.ui.market.adapters.ProductViewAdapter;

import java.util.ArrayList;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static java.util.Objects.requireNonNull;

public class ProductListActivity extends BaseActivity implements Injectable, SwipeRefreshLayout.OnRefreshListener {

    private Map<String, String> map = new android.support.v4.util.ArrayMap<>();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Inject
    ViewModelFactory factory;
    @Inject
    ProductListActivityViewModel viewModel;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.error_view)
    View error_view;
    @BindView(R.id.empty)
    View empty_view;
    @BindView(R.id.category_item_name)
    TextView name;
    @BindView(R.id.productList)
    RecyclerView recyclerView;
    private int current_page = 1, last_page = 1;
    private Unbinder unbinder;
    private ProductViewAdapter adapter;
    private boolean loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        unbinder = ButterKnife.bind(ProductListActivity.this);

        adapter = new ProductViewAdapter(2);
        setSupportActionBar(toolbar);
        requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        ProductCategory category = getIntent().getParcelableExtra("org.zeniafrik.models.ProductCategory");


        map.put("region", "all");
        map.put("priceMin", "0");
        map.put("priceMax", "5000");
        map.put("category_id", String.valueOf(category.getId()));

        name.setText(category.getName());

        viewModel = ViewModelProviders.of(ProductListActivity.this, factory).get
                (ProductListActivityViewModel.class);

        viewModel.getProducts().observe(ProductListActivity.this, product_response -> {
            switch (requireNonNull(product_response).status) {
                case SUCCESS:
                    loaded_ok(requireNonNull(product_response.data).getData(), product_response.data.getMeta());
                    break;
                case ERROR:
                    error_occurred();
                case LOADING:
                    refreshLayout.setRefreshing(true);
            }
        });

        refreshLayout.setOnRefreshListener(ProductListActivity.this);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(-1) && (current_page <= last_page) && !loading)
                    load_more();
            }
        });

        int spanCount = getResources().getInteger(R.integer.deviceSize) == 720 ? 3 : 2;
        recyclerView.setLayoutManager(new GridLayoutManager(ProductListActivity.this, spanCount, GridLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new ItemOffsetDecoration(ProductListActivity.this, R.dimen.itemoffset));
        recyclerView.setAdapter(adapter);

        init_load();
    }

    private void error_occurred() {
        refreshLayout.setRefreshing(false);
        loading = false;
        if (!error_view.isShown()) error_view.setVisibility(View.VISIBLE);
        if (empty_view.isShown()) empty_view.setVisibility(View.GONE);
        if (recyclerView.isShown()) recyclerView.setVisibility(View.GONE);
    }

    private void loaded_ok(ArrayList<BaseProduct> products, meta meta) {
        loading = false;
        if (!products.isEmpty()) {
            if (current_page == 1) adapter.setProductList(products);
            else adapter.add_moreProducts(products);
            extract_meta_info(meta);
            refreshView();
        } else renderError();

        if (error_view.isShown()) error_view.setVisibility(View.GONE);
        refreshLayout.setRefreshing(false);
    }

    private void refreshView() {
        if (!recyclerView.isShown()) recyclerView.setVisibility(View.VISIBLE);
        if (empty_view.isShown()) empty_view.setVisibility(View.GONE);
    }

    private void renderError() {
        if (!empty_view.isShown()) empty_view.setVisibility(View.VISIBLE);
        if (recyclerView.isShown()) recyclerView.setVisibility(View.GONE);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        adapter = null;
        map = null;
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.z_x_menu, menu);
        return true;
    }

    private void extract_meta_info(meta meta) {
        if (meta.current_page == 1) last_page = meta.last_page;
        if (meta.current_page <= last_page) current_page++;
    }

    private void load_more() {
        map.put("page", String.valueOf(current_page));
        init_load();
    }

    private void init_load() {
        refreshLayout.setRefreshing(true);
        viewModel.setFilterMap(map);
    }


    @Override
    public void onRefresh() {
        current_page = 1;
        last_page = 1;
        load_more();
    }
}
