package org.zeniafrik.ui.user;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import org.zeniafrik.R;
import org.zeniafrik.di.Injectable;
import org.zeniafrik.factory.ViewModelFactory;
import org.zeniafrik.helper.BaseActivity;
import org.zeniafrik.models.ProductCategory;
import org.zeniafrik.ui.extras.GenericAlertDialog;
import org.zeniafrik.ui.user.adapters.CategoryListAdapter;
import org.zeniafrik.ui.user.viewmodels.PostSharedViewModel;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static java.util.Objects.requireNonNull;

public class PostProductActivity extends BaseActivity implements Injectable, DialogInterface.OnCancelListener{

    private Unbinder unbinder;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.category_list)
    ListView category_list;

    @BindString(R.string.something_went_wrong)
    String error;

    @Inject
    ViewModelFactory factory;

    @BindView(R.id.loading)
    View view;

    @Inject
    PostSharedViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_product);
        unbinder = ButterKnife.bind(PostProductActivity.this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CategoryListAdapter adapter = new CategoryListAdapter(PostProductActivity.this, R.layout.simple_product_category_item);


        viewModel = ViewModelProviders.of(this, factory).get(PostSharedViewModel.class);
        viewModel.getCategoryList().observe(this, category_response -> {
            if (category_response != null) {
                switch (category_response.status) {

                    case SUCCESS:
                        adapter.addAll(requireNonNull(category_response.data).getData());
                        view.setVisibility(View.GONE);
                        break;
                    case ERROR:
                        new GenericAlertDialog(this,false,this, error, 2).show();
                        view.setVisibility(View.GONE);
                        break;
                }
            }

        });

        category_list.setAdapter(adapter);

        category_list.setOnItemClickListener((adapterView, view, i, l) -> {
            ProductCategory category = (ProductCategory) category_list.getItemAtPosition(i);
            viewModel.setSelectedCategory(category);
            Intent intent = new Intent(this,AdDetailActivity.class);
            intent.putExtra("org.zeniafrik.models.ProductCategory", category);
            startActivity(intent);
        });


    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        finish();
    }
}
