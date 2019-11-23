package org.zeniafrik.api;

import android.arch.lifecycle.LiveData;

import org.zeniafrik.models.BaseMarketComponents;
import org.zeniafrik.models.BaseProduct;
import org.zeniafrik.models.Credential;
import org.zeniafrik.models.Feed;
import org.zeniafrik.models.Order;
import org.zeniafrik.models.ProductCategory;
import org.zeniafrik.models.ProductDetail;
import org.zeniafrik.models.RegisterFormObject;
import org.zeniafrik.models.UserLocalObject;
import org.zeniafrik.ui.market.ViewModels.OrderPlacementViewModel;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApplicationService {

    @POST("login")
    LiveData<ApiResponse<AuthenticationResponse>> attemptLogin(@Body Credential credential);

    @POST("register")
    LiveData<ApiResponse<AuthenticationResponse>> attemptRegister(@Body RegisterFormObject formObject);

    @GET("market-components")
    LiveData<ApiResponse<ZeniNetResponse<BaseMarketComponents>>> marketComponents();

    @GET("product/{id}")
    LiveData<ApiResponse<ZeniNetResponse<ProductDetail>>> productDetail(@Path("id") int id);

    @GET("similar/{product_id}/{category_id}")
    LiveData<ApiResponse<ZeniNetResponse<ArrayList<BaseProduct>>>> similarProducts(@Path("product_id") int pid, @Path("category_id") int id);

    @GET("categories")
    LiveData<ApiResponse<ZeniNetResponse<ArrayList<ProductCategory>>>> getCategories();

    @GET("products-by-category")
    LiveData<ApiResponse<ZeniNetResponse<ArrayList<BaseProduct>>>> getProductsByCategory(@QueryMap Map<String, String> filterMap);

    @GET("feeds")
    LiveData<ApiResponse<ZeniNetResponse<ArrayList<Feed>>>> getFeeds(@Query("page") int next_page);

    @GET("logout")
    LiveData<ApiResponse<String>> logout();

    @GET("sync-user")
    LiveData<ApiResponse<UserLocalObject>> getUserObject();

    @POST("create-ad")
    LiveData<ApiResponse<ZeniNetResponse<Integer>>> createAd(@Body BaseProduct product);


    @FormUrlEncoded
    @POST("up-images/{product_id}")
    LiveData<ApiResponse<String>> uploadAdImage(@Path("product_id") int product_id,@Field("images[]") ArrayList<String> images);

    @GET("my-products")
    LiveData<ApiResponse<ZeniNetResponse<ArrayList<BaseProduct>>>> getUserProducts();

    @GET("my-orders")
    LiveData<ApiResponse<ZeniNetResponse<ArrayList<Order>>>> getUserOrders();

    @POST("create-order")
    LiveData<ApiResponse<ZeniNetResponse<OrderPlacementViewModel.OrderResponse>>> placeOrder(@Body Order order);

    @GET("cities/{region_name}")
    LiveData<ApiResponse<ZeniNetResponse<String[]>>> getCitiesByRegion(@Path("region_name") String region);
}
