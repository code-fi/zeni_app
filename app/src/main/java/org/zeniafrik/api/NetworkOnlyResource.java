package org.zeniafrik.api;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

import org.zeniafrik.helper.AppExecutors;
import org.zeniafrik.helper.Resource;
import org.zeniafrik.util.Objects;

/**
 * Created by BraDev ${LOCALE} on 1/12/2018.
 */
public abstract class NetworkOnlyResource<ResultType> {
    private final AppExecutors appExecutors;
    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();
    private int retry_count = 3;

    @MainThread
    public NetworkOnlyResource(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
        result.setValue(Resource.loading(null));
        fetchFromNetwork();
    }

    @MainThread
    private void setValue(Resource<ResultType> newValue) {
        if (!Objects.equals(result.getValue(), newValue)) {
            result.setValue(newValue);
        }
    }

    private void fetchFromNetwork() {
        LiveData<ApiResponse<ResultType>> apiResponse = createCall();

        result.addSource(apiResponse, response -> {
            result.removeSource(apiResponse);

            if (response.isSuccessful()) appExecutors.diskIO().execute(() -> {
                ResultType res = processResponse(response);
                if (shouldSaveDb()) saveCallResult(res);
                appExecutors.mainThread().execute(() -> result.addSource(apiResponse, newData -> setValue(Resource.success(res))));
            });
            else {
                if (shouldRetry() && response.code == 500 && retry_count > 0) {
                    retry_count -= 1;
                    fetchFromNetwork();
                    return;
                }
                result.addSource(apiResponse, newData -> setValue(Resource.error(response.errorMessage, null)));
            }
        });
    }


    public LiveData<Resource<ResultType>> asLiveData() {
        return result;
    }

    @WorkerThread
    private ResultType processResponse(ApiResponse<ResultType> response) {
        return response.body;
    }

    @WorkerThread
    protected abstract void saveCallResult(@NonNull ResultType item);

    @MainThread
    protected abstract boolean shouldSaveDb();


    @MainThread
    protected abstract boolean shouldRetry();
    @NonNull
    @MainThread
    protected abstract LiveData<ApiResponse<ResultType>> createCall();


}