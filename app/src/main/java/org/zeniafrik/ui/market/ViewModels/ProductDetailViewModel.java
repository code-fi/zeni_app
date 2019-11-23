package org.zeniafrik.ui.market.ViewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import org.zeniafrik.api.ZeniNetResponse;
import org.zeniafrik.helper.Resource;
import org.zeniafrik.models.BaseProduct;
import org.zeniafrik.models.ProductDetail;
import org.zeniafrik.repository.MarketRepository;

import java.util.ArrayList;

import javax.inject.Inject;

import static android.arch.lifecycle.Transformations.switchMap;

public class ProductDetailViewModel extends ViewModel {

    private final LiveData<Resource<ZeniNetResponse<ProductDetail>>> productDetail;
    private final MutableLiveData<Integer> productId = new MutableLiveData<>();
    private final MutableLiveData<mMap> nMap = new MutableLiveData<>();
    private final LiveData<Resource<ZeniNetResponse<ArrayList<BaseProduct>>>> similarProducts;


    @Inject
    public ProductDetailViewModel(MarketRepository marketRepository) {
        productDetail = switchMap(productId, marketRepository::getProductDetail);
        similarProducts = switchMap(nMap, map -> marketRepository.getSimilarProducts(map.getId(), map.getCategory_id()));
    }

    public LiveData<Resource<ZeniNetResponse<ProductDetail>>> getProduct(int id) {
        productId.setValue(id);
        return productDetail;
    }


    public LiveData<Resource<ZeniNetResponse<ArrayList<BaseProduct>>>> getSimilarProducts() {
        return similarProducts;
    }

    public void setnMap(int id, int category_id) {
        nMap.setValue(new mMap(id, category_id));
    }
}



class mMap {
    private int id, category_id;

    public mMap(int id, int category_id) {
        this.id = id;
        this.category_id = category_id;
    }

    public int getId() {
        return id;
    }

    public int getCategory_id() {
        return category_id;
    }
}