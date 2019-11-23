package org.zeniafrik.ui.market;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.zeniafrik.R;
import org.zeniafrik.di.Injectable;
import org.zeniafrik.factory.ViewModelFactory;
import org.zeniafrik.ui.market.ViewModels.SearchFlowViewModel;

import java.util.HashMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;

import static java.util.Objects.requireNonNull;

public class SearchFlowActivity extends AppCompatActivity implements Injectable {

    private String region = "All Regions", city = null, product_name = null, category = null, minPrice = "0", maxPrice = "5000";


    @Inject
    ViewModelFactory factory;

    @Inject
    SearchFlowViewModel searchFlowViewModel;


    @BindView(R.id.filter_wrapper)
    View filter_wrapper;

    @OnClick(R.id.search_close_btn)
    public void onClose() {
        finish();
    }

    @OnTextChanged(R.id.product_name)
    public void onProductNameChange(CharSequence text) {
        product_name = text.toString();
    }

    @OnTextChanged(R.id.price_min)
    public void onMinPriceChange(CharSequence text) {
        minPrice = text.toString();
    }

    @OnTextChanged(R.id.price_max)
    public void onMaxPriceChange(CharSequence text) {
        maxPrice = text.toString();
    }


    @OnItemSelected(R.id.region_select)
    public void onRegionChange(AppCompatSpinner spinner, int position) {
        String tempRegion = spinner.getItemAtPosition(position).toString();
        if (!tempRegion.equalsIgnoreCase(region) || (position != 0 && region.equalsIgnoreCase("All Regions"))) {
            region = tempRegion;
            searchFlowViewModel.setSelectedRegion(region);
        }

    }

    @BindView(R.id.city_select)
    AppCompatSpinner city_select;

    @OnItemSelected(R.id.city_select)
    public void onCityChange(AppCompatSpinner spinner, int position) {
        String tempCity = spinner.getItemAtPosition(position).toString();
        if (!tempCity.equalsIgnoreCase(city)) city = tempCity;
    }

    @OnItemSelected(R.id.city_select)
    public void onCategoryChange(AppCompatSpinner spinner, int position) {
        String tempCategory = spinner.getItemAtPosition(position).toString();
        if (!tempCategory.equalsIgnoreCase(category)) category = tempCategory;
    }

    @OnClick(R.id.show_fll)
    public void toggleFilterView() {
        if (!filter_wrapper.isShown()) filter_wrapper.setVisibility(View.VISIBLE);
        else filter_wrapper.setVisibility(View.GONE);
    }

    @OnClick(R.id.search_button)
    public void onSearch() {
        HashMap<String, String> map = new HashMap<>();
        map.put("region", region);
        map.put("city", city);
        map.put("product_name", product_name);
        map.put("minPrice", minPrice);
        map.put("maxPrice", maxPrice);

        Intent searchResultIntent = new Intent(SearchFlowActivity.this, SearchResult.class);
        searchResultIntent.putExtra("searchQueryMap", map);
        startActivity(searchResultIntent);
        finish();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchflow_activity);
        ButterKnife.bind(this);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        city_select.setAdapter(arrayAdapter);

        searchFlowViewModel = ViewModelProviders.of(SearchFlowActivity.this, factory).get(SearchFlowViewModel.class);
        searchFlowViewModel.getCities().observe(SearchFlowActivity.this, response_data -> {
            switch (requireNonNull(response_data).status) {
                case ERROR:
                    Toast.makeText(SearchFlowActivity.this, response_data.message, Toast.LENGTH_SHORT).show();
                    break;
                case SUCCESS:
                    arrayAdapter.clear();
                    if (response_data.data != null) {
                        if(!city_select.isShown())city_select.setVisibility(View.VISIBLE);
                        arrayAdapter.addAll(response_data.data.getData());
                        arrayAdapter.notifyDataSetChanged();
                    }
                    break;

            }
        });
    }

}

