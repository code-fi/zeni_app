package org.zeniafrik.ui.user.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import org.zeniafrik.api.ZeniNetResponse;
import org.zeniafrik.helper.Resource;
import org.zeniafrik.models.Order;
import org.zeniafrik.repository.MarketRepository;

import java.util.ArrayList;

import javax.inject.Inject;

public class MyOrdersViewModel extends ViewModel {

    private final MutableLiveData<Integer> refresh_code = new MutableLiveData<>();
    private final LiveData<Resource<ZeniNetResponse<ArrayList<Order>>>> orders;

    @Inject
    public MyOrdersViewModel(MarketRepository marketRepository) {
        orders = Transformations.switchMap(refresh_code, code -> marketRepository.getUserOrders());
    }

    public LiveData<Resource<ZeniNetResponse<ArrayList<Order>>>> getOrders() {
        return orders;
    }

    public void refresh() {
        refresh_code.setValue(1);
    }
}

