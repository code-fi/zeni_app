package org.zeniafrik.ui.market.ViewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import org.zeniafrik.api.ZeniNetResponse;
import org.zeniafrik.helper.Resource;
import org.zeniafrik.models.BaseProduct;
import org.zeniafrik.repository.MarketRepository;

import java.util.ArrayList;
import java.util.Map;

import javax.inject.Inject;

public class ProductListActivityViewModel extends ViewModel {

    private final LiveData<Resource<ZeniNetResponse<ArrayList<BaseProduct>>>> products;
    private final MutableLiveData<Map<String, String>> filterMap = new MutableLiveData<>();

    @Inject
    public ProductListActivityViewModel(MarketRepository repository) {
        products = Transformations.switchMap(filterMap, repository::getProductByCategory);
    }

    public LiveData<Resource<ZeniNetResponse<ArrayList<BaseProduct>>>> getProducts() {
        return products;
    }

    public void setFilterMap(Map<String, String> filterMap) {
        this.filterMap.setValue(filterMap);
    }
}