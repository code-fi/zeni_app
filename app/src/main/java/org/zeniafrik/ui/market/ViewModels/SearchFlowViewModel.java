package org.zeniafrik.ui.market.ViewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import org.zeniafrik.api.ZeniNetResponse;
import org.zeniafrik.helper.Resource;
import org.zeniafrik.repository.MarketRepository;

import javax.inject.Inject;

public class SearchFlowViewModel extends ViewModel {
    private final MutableLiveData<String> region = new MutableLiveData<>();
    private final LiveData<Resource<ZeniNetResponse<String[]>>> cities;

    @Inject
    public SearchFlowViewModel(MarketRepository repository) {
        cities = Transformations.switchMap(region,repository::getCities);
    }

    public void setSelectedRegion(String region){
        this.region.setValue(region);
    }

    public LiveData<Resource<ZeniNetResponse<String[]>>> getCities() {
        return cities;
    }
}
