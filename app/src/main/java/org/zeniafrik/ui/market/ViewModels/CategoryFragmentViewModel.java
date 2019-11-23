package org.zeniafrik.ui.market.ViewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import org.zeniafrik.api.ZeniNetResponse;
import org.zeniafrik.helper.Resource;
import org.zeniafrik.models.ProductCategory;
import org.zeniafrik.repository.MarketRepository;

import java.util.ArrayList;

import javax.inject.Inject;

public class CategoryFragmentViewModel extends ViewModel {


    private final MutableLiveData<Integer> refresh_code = new MutableLiveData<>();
    private final LiveData<Resource<ZeniNetResponse<ArrayList<ProductCategory>>>> categories;

    @Inject
    public CategoryFragmentViewModel(MarketRepository marketRepository) {
        categories = Transformations.switchMap(refresh_code, code -> marketRepository.getCategories());
    }

    public LiveData<Resource<ZeniNetResponse<ArrayList<ProductCategory>>>> getCategories() {
        return categories;
    }

    public void refresh() {
        refresh_code.setValue(1);
    }
}
