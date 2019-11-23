package org.zeniafrik.ui.user.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import org.zeniafrik.api.ZeniNetResponse;
import org.zeniafrik.helper.Resource;
import org.zeniafrik.models.BaseProduct;
import org.zeniafrik.repository.MarketRepository;

import java.util.ArrayList;

import javax.inject.Inject;

public class MyProductsViewModel extends ViewModel {
    private final MutableLiveData<Integer> refresh_code = new MutableLiveData<>();
    private final LiveData<Resource<ZeniNetResponse<ArrayList<BaseProduct>>>> products;

    @Inject
    public MyProductsViewModel(MarketRepository marketRepository) {
        products = Transformations.switchMap(refresh_code, code -> marketRepository.getUserProducts());
    }

    public LiveData<Resource<ZeniNetResponse<ArrayList<BaseProduct>>>> getProducts() {
        return products;
    }

    public void refresh() {
        refresh_code.setValue(1);
    }}
