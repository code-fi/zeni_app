package org.zeniafrik.ui.user.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.net.Uri;
import android.util.Log;

import org.zeniafrik.api.ZeniNetResponse;
import org.zeniafrik.helper.Resource;
import org.zeniafrik.models.BaseProduct;
import org.zeniafrik.models.ProductCategory;
import org.zeniafrik.repository.MarketRepository;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

public class PostSharedViewModel extends ViewModel {

    private final MutableLiveData<BaseProduct> createdProduct = new MutableLiveData<>();
    private final MutableLiveData<ProductCategory> selectedCategory = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Uri>> imageUriList = new MutableLiveData<>();
    private LiveData<Resource<ZeniNetResponse<ArrayList<ProductCategory>>>> categoryList;
    private final LiveData<Resource<ZeniNetResponse<Integer>>> product_id;
    private final MarketRepository repository;

    @Inject
    public PostSharedViewModel(MarketRepository marketRepository) {
        repository = marketRepository;
        product_id = Transformations.switchMap(createdProduct, repository::createAd);
    }


    public LiveData<Resource<ZeniNetResponse<ArrayList<ProductCategory>>>> getCategoryList() {
        if (categoryList == null) categoryList = repository.getCategories();
        return categoryList;
    }

    public MutableLiveData<ArrayList<Uri>> getImageUriList() {
        return imageUriList;
    }

    public MutableLiveData<BaseProduct> getCreatedProduct() {
        return createdProduct;
    }

    public void setCreatedProduct(BaseProduct product) {
        createdProduct.setValue(product);
    }

    public MutableLiveData<ProductCategory> getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(ProductCategory category) {
        this.selectedCategory.setValue(category);
    }

    public LiveData<Resource<ZeniNetResponse<Integer>>> getProduct_id() {
        return product_id;
    }

    public void setImageUriList(int position, Uri uri) {
        ArrayList<Uri> uris = imageUriList.getValue();
        if (uris != null) {
            uris.remove(position);
            if (position != 0 && uris.get(0) == null){
                uris.add(0,uri);
            }else{
                uris.add(position, uri);
            }
            imageUriList.setValue(uris);
        }
    }

    public void initImageList() {
        imageUriList.setValue(new ArrayList<>(Arrays.asList(null, null, null)));
    }

    public LiveData<Resource<String>> uploadImages(ArrayList<String> imageStrings, int product_id) {
        return repository.uploadImages(imageStrings,product_id);

    }
}
