package org.zeniafrik.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import org.zeniafrik.api.ApiResponse;
import org.zeniafrik.api.ApplicationService;
import org.zeniafrik.api.NetworkOnlyResource;
import org.zeniafrik.api.ZeniNetResponse;
import org.zeniafrik.helper.AppExecutors;
import org.zeniafrik.helper.Resource;
import org.zeniafrik.models.BaseMarketComponents;
import org.zeniafrik.models.BaseProduct;
import org.zeniafrik.models.Feed;
import org.zeniafrik.models.Order;
import org.zeniafrik.models.ProductCategory;
import org.zeniafrik.models.ProductDetail;
import org.zeniafrik.ui.market.ViewModels.OrderPlacementViewModel.OrderResponse;

import java.util.ArrayList;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MarketRepository {

    private final ApplicationService applicationService;
    private final AppExecutors executors;

    @Inject
    MarketRepository(AppExecutors executors, ApplicationService applicationService) {
        this.applicationService = applicationService;
        this.executors = executors;
    }


    public LiveData<Resource<ZeniNetResponse<BaseMarketComponents>>> getMarketComponents() {
        return new NetworkOnlyResource<ZeniNetResponse<BaseMarketComponents>>(executors) {

            @Override
            protected void saveCallResult(@NonNull ZeniNetResponse<BaseMarketComponents> item) {
            }

            @Override
            protected boolean shouldSaveDb() {
                return false;
            }

            @Override
            protected boolean shouldRetry() {
                return true;
            }


            @NonNull
            @Override
            protected LiveData<ApiResponse<ZeniNetResponse<BaseMarketComponents>>> createCall() {
                return applicationService.marketComponents();
            }
        }.asLiveData();
    }


    public LiveData<Resource<ZeniNetResponse<ProductDetail>>> getProductDetail(int id) {
        return new NetworkOnlyResource<ZeniNetResponse<ProductDetail>>(executors) {
            @Override
            protected void saveCallResult(@NonNull ZeniNetResponse<ProductDetail> item) {
            }

            @Override
            protected boolean shouldSaveDb() {
                return false;
            }

            @Override
            protected boolean shouldRetry() {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ZeniNetResponse<ProductDetail>>> createCall() {
                return applicationService.productDetail(id);
            }
        }.asLiveData();
    }

    public LiveData<Resource<ZeniNetResponse<ArrayList<BaseProduct>>>> getSimilarProducts(int pid, int id) {
        return new NetworkOnlyResource<ZeniNetResponse<ArrayList<BaseProduct>>>(executors) {
            @Override
            protected void saveCallResult(@NonNull ZeniNetResponse<ArrayList<BaseProduct>> item) {
            }

            @Override
            protected boolean shouldSaveDb() {
                return false;
            }

            @Override
            protected boolean shouldRetry() {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ZeniNetResponse<ArrayList<BaseProduct>>>> createCall() {
                return applicationService.similarProducts(pid, id);
            }
        }.asLiveData();
    }

    public LiveData<Resource<ZeniNetResponse<ArrayList<ProductCategory>>>> getCategories() {
        return new NetworkOnlyResource<ZeniNetResponse<ArrayList<ProductCategory>>>(executors) {

            @Override
            protected void saveCallResult(@NonNull ZeniNetResponse<ArrayList<ProductCategory>> item) {

            }
            @Override
            protected boolean shouldSaveDb() {
                return false;
            }

            @Override
            protected boolean shouldRetry() {
                return false;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ZeniNetResponse<ArrayList<ProductCategory>>>> createCall() {
                return applicationService.getCategories();
            }
        }.asLiveData();
    }

    public LiveData<Resource<ZeniNetResponse<ArrayList<BaseProduct>>>> getProductByCategory(Map<String,String> filterMap) {
        return new NetworkOnlyResource<ZeniNetResponse<ArrayList<BaseProduct>>>(executors) {

            @Override
            protected void saveCallResult(@NonNull ZeniNetResponse<ArrayList<BaseProduct>> item) {

            }

            @Override
            protected boolean shouldSaveDb() {
                return false;
            }

            @Override
            protected boolean shouldRetry() {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ZeniNetResponse<ArrayList<BaseProduct>>>> createCall() {
                return applicationService.getProductsByCategory(filterMap);
            }
        }.asLiveData();
    }

    public LiveData<Resource<ZeniNetResponse<ArrayList<Feed>>>> getFeeds(int next_page) {
        return new NetworkOnlyResource<ZeniNetResponse<ArrayList<Feed>>>(executors) {

            @Override
            protected void saveCallResult(@NonNull ZeniNetResponse<ArrayList<Feed>> item) {
            }

            @Override
            protected boolean shouldSaveDb() {
                return false;
            }

            @Override
            protected boolean shouldRetry() {
                return true;
            }


            @NonNull
            @Override
            protected LiveData<ApiResponse<ZeniNetResponse<ArrayList<Feed>>>> createCall() {
                return applicationService.getFeeds(next_page);
            }

        }.asLiveData();
    }

    public LiveData<Resource<ZeniNetResponse<Integer>>> createAd(BaseProduct product) {
        return new NetworkOnlyResource<ZeniNetResponse<Integer>>(executors){

            @Override
            protected void saveCallResult(@NonNull ZeniNetResponse<Integer> item) {

            }


            @Override
            protected boolean shouldSaveDb() {
                return false;
            }

            @Override
            protected boolean shouldRetry() {
                return false;
            }


            @NonNull
            @Override
            protected LiveData<ApiResponse<ZeniNetResponse<Integer>>> createCall() {
                return applicationService.createAd(product);
            }
        }.asLiveData();
    }

    public LiveData<Resource<String>> uploadImages(ArrayList<String> imageStrings, int product_id) {
        return new NetworkOnlyResource<String>(executors){


            @Override
            protected void saveCallResult(@NonNull String item) {

            }

            @Override
            protected boolean shouldSaveDb() {
                return false;
            }

            @Override
            protected boolean shouldRetry() {
                return false;
            }


            @NonNull
            @Override
            protected LiveData<ApiResponse<String>> createCall() {
                Log.d("44444444444444444444444",String.valueOf(imageStrings.size()));
                return applicationService.uploadAdImage(product_id,imageStrings);
            }
        }.asLiveData();
    }

    public LiveData<Resource<ZeniNetResponse<ArrayList<BaseProduct>>>> getUserProducts() {
        return new NetworkOnlyResource<ZeniNetResponse<ArrayList<BaseProduct>>>(executors) {

            @Override
            protected void saveCallResult(@NonNull ZeniNetResponse<ArrayList<BaseProduct>> item) {}

            @Override
            protected boolean shouldSaveDb() {
                return false;
            }

            @Override
            protected boolean shouldRetry() {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ZeniNetResponse<ArrayList<BaseProduct>>>> createCall() {
                return applicationService.getUserProducts();
            }
        }.asLiveData();
    }

    public LiveData<Resource<ZeniNetResponse<ArrayList<Order>>>> getUserOrders() {
        return new NetworkOnlyResource<ZeniNetResponse<ArrayList<Order>>>(executors) {

            @Override
            protected void saveCallResult(@NonNull ZeniNetResponse<ArrayList<Order>> item) {}

            @Override
            protected boolean shouldSaveDb() {
                return false;
            }

            @Override
            protected boolean shouldRetry() {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ZeniNetResponse<ArrayList<Order>>>> createCall() {
                return applicationService.getUserOrders();
            }
        }.asLiveData();
    }

    public LiveData<Resource<ZeniNetResponse<OrderResponse>>> placeOrder(Order order) {
        return new NetworkOnlyResource<ZeniNetResponse<OrderResponse>>(executors) {

            @Override
            protected void saveCallResult(@NonNull ZeniNetResponse<OrderResponse> response) {
//                OrderResponse res = response.getData();

//                order.setId(res.id);
//                order.
            }

            @Override
            protected boolean shouldSaveDb() {
                return true;
            }

            @Override
            protected boolean shouldRetry() {
                return false;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ZeniNetResponse<OrderResponse>>> createCall() {
                return applicationService.placeOrder(order);
            }
        }.asLiveData();
    }

    public LiveData<Resource<ZeniNetResponse<String[]>>> getCities(String region) {
        return new NetworkOnlyResource<ZeniNetResponse<String[]>>(executors) {

            @Override
            protected void saveCallResult(@NonNull ZeniNetResponse<String[]> response) {}

            @Override
            protected boolean shouldSaveDb() {
                return false;
            }

            @Override
            protected boolean shouldRetry() {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ZeniNetResponse<String[]>>> createCall() {
                return applicationService.getCitiesByRegion(region);
            }
        }.asLiveData();
    }
}