package org.zeniafrik.api;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by BraDev ${LOCALE} on 1/12/2018.
 */

public class LiveDataCallAdapter<R> implements CallAdapter<R, LiveData<ApiResponse<R>>> {
    private final Type responseType;

    public LiveDataCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public LiveData<ApiResponse<R>> adapt(@NonNull Call<R> call) {
        return new LiveData<ApiResponse<R>>() {
            AtomicBoolean started = new AtomicBoolean(false);

            @Override
            protected void onActive() {
                super.onActive();
                if (started.compareAndSet(false, true)) {
                    call.enqueue(new Callback<R>() {
                        @Override
                        public void onResponse(@NonNull Call<R> call, @NonNull Response<R> response) {
                            postValue(new ApiResponse<>(response));
                        }

                        @Override
                        public void onFailure(@NonNull Call<R> call, @NonNull Throwable throwable) {
                            if (!call.isCanceled()) postValue(new ApiResponse<R>(throwable));
                        }
                    });
                }
            }

            @Override
            protected void onInactive() {
                super.onInactive();
                call.cancel();
            }
        };
    }
}