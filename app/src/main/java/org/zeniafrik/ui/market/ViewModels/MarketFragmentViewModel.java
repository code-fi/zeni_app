package org.zeniafrik.ui.market.ViewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import org.zeniafrik.api.ZeniNetResponse;
import org.zeniafrik.helper.Resource;
import org.zeniafrik.models.BaseMarketComponents;
import org.zeniafrik.repository.MarketRepository;

import javax.inject.Inject;

import static android.arch.lifecycle.Transformations.switchMap;

public class MarketFragmentViewModel extends ViewModel {

    private final LiveData<Resource<ZeniNetResponse<BaseMarketComponents>>> products;

    private MutableLiveData<Boolean> refresh = new MutableLiveData<>();

    @Inject
    MarketFragmentViewModel(MarketRepository marketRepository) {
        products = switchMap(refresh, refresh_state -> marketRepository.getMarketComponents());
    }

    public void setRefresh() {
        refresh.setValue(true);
    }

    public LiveData<Resource<ZeniNetResponse<BaseMarketComponents>>> getMarketComponents() {
        return products;
    }

}
