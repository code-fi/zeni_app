package org.zeniafrik.ui.user.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import org.zeniafrik.helper.Resource;
import org.zeniafrik.models.UserLocalObject;
import org.zeniafrik.repository.AuthenticationRepository;

import javax.inject.Inject;

public class DashboardViewModel extends ViewModel {

    private final LiveData<Resource<UserLocalObject>> userLocalObject;
    private AuthenticationRepository repository;
    @Inject
    public DashboardViewModel(AuthenticationRepository repository){
        this.repository = repository;
        userLocalObject = repository.getLocalUserObject();
    }

    public LiveData<Resource<UserLocalObject>> getUserLocalObject() {
        return userLocalObject;
    }

    public void logout() {
        repository.logout();
    }
}
