package org.zeniafrik.repository;

import android.arch.lifecycle.LiveData;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.zeniafrik.api.ApiResponse;
import org.zeniafrik.api.ApplicationService;
import org.zeniafrik.api.AuthenticationResponse;
import org.zeniafrik.api.NetworkBoundResource;
import org.zeniafrik.api.NetworkOnlyResource;
import org.zeniafrik.db.UserDao;
import org.zeniafrik.helper.AppExecutors;
import org.zeniafrik.helper.Resource;
import org.zeniafrik.models.Credential;
import org.zeniafrik.models.RegisterFormObject;
import org.zeniafrik.models.UserLocalObject;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class AuthenticationRepository {

    private final AppExecutors executors;
    private final ApplicationService applicationService;
    private final SharedPreferences localPref;
    private final UserDao userDao;

    @Inject
    AuthenticationRepository(AppExecutors executors, ApplicationService applicationService, SharedPreferences pref, UserDao userDao) {
        this.applicationService = applicationService;
        this.executors = executors;
        localPref = pref;
        this.userDao = userDao;
    }

    public LiveData<Resource<AuthenticationResponse>> attemptLogin(Credential credential) {
        return new NetworkOnlyResource<AuthenticationResponse>(executors) {
            @Override
            protected void saveCallResult(@NonNull AuthenticationResponse response) {
                final AuthenticationResponse.MicroResponse microResponse = response.getData();
                AuthenticationResponse.ClientInfo clientInfo = microResponse.getClientinfo();
                userDao.save(microResponse.getProfile());
                localPref.edit()
                        .putBoolean("isLoggedIn", true)
                        .putString("token", clientInfo.getAccess_token())
                        .putString("refresh_token", clientInfo.getRefresh_token())
                        .putInt("expires_in", clientInfo.getExpires_in())
                        .apply();
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
            protected LiveData<ApiResponse<AuthenticationResponse>> createCall() {
                return applicationService.attemptLogin(credential);
            }
        }.asLiveData();
    }

    public LiveData<Resource<AuthenticationResponse>> attemptRegister(RegisterFormObject formObject) {
        return new NetworkOnlyResource<AuthenticationResponse>(executors) {
            @Override
            protected void saveCallResult(@NonNull AuthenticationResponse response) {
                final AuthenticationResponse.MicroResponse microResponse = response.getData();
                AuthenticationResponse.ClientInfo clientInfo = microResponse.getClientinfo();
                userDao.save(microResponse.getProfile());

                localPref.edit()
                        .putBoolean("isLoggedIn", true)
                        .putString("token", clientInfo.getAccess_token())
                        .putString("refresh_token", clientInfo.getRefresh_token())
                        .putInt("expires_in", clientInfo.getExpires_in())
                        .apply();
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
            protected LiveData<ApiResponse<AuthenticationResponse>> createCall() {
                return applicationService.attemptRegister(formObject);
            }
        }.asLiveData();
    }

    public LiveData<Resource<String>> logout() {
        return new NetworkOnlyResource<String>(executors) {
            @Override
            protected void saveCallResult(@NonNull String response) {
                userDao.deleteAll();
                localPref.edit().clear().apply();
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
            protected LiveData<ApiResponse<String>> createCall() {
                return applicationService.logout();
            }
        }.asLiveData();
    }

    public LiveData<Resource<UserLocalObject>> getLocalUserObject() {
        return new NetworkBoundResource<UserLocalObject, UserLocalObject>(executors) {
            @Override
            protected void saveCallResult(@NonNull UserLocalObject response) {
                userDao.updateUserLocal(response);
            }

            @Override
            protected boolean shouldFetch(@Nullable UserLocalObject data) {
                return false;
            }

            @Override
            protected boolean shouldLoadDb() {
                return true;
            }

            @Override
            protected boolean shouldSaveDb() {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<UserLocalObject> loadFromDb() {
                return userDao.getUser();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<UserLocalObject>> createCall() {
                return applicationService.getUserObject();
            }
        }.asLiveData();
    }

}
