package org.zeniafrik.ui.market.ViewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import org.zeniafrik.api.ZeniNetResponse;
import org.zeniafrik.helper.Resource;
import org.zeniafrik.repository.MarketRepository;
import org.zeniafrik.models.Order;

import javax.inject.Inject;

import static android.arch.lifecycle.Transformations.switchMap;

public class OrderPlacementViewModel extends ViewModel {

    private final LiveData<Resource<ZeniNetResponse<OrderResponse>>> orderResponse;
    private final MutableLiveData<Order> orderData = new MutableLiveData<>();

    @Inject
    public OrderPlacementViewModel(MarketRepository marketRepository){
        orderResponse = switchMap(orderData,marketRepository::placeOrder);
    }


    public void placeOrder(Order order){
        this.orderData.setValue(order);
    }

    public LiveData<Resource<ZeniNetResponse<OrderResponse>>> orderResponseSubscription(){
        return this.orderResponse;    
    }

    public static class OrderResponse{
        public final String uuid;
        public final int id;

        OrderResponse(int id, String uuid){
            this.id = id;
            this.uuid = uuid;
        }
    }
}