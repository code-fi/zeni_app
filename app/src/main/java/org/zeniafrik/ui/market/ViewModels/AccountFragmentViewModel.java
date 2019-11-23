package org.zeniafrik.ui.market.ViewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import org.zeniafrik.helper.Resource;
import org.zeniafrik.models.UserLocalObject;
import org.zeniafrik.repository.AuthenticationRepository;

import javax.inject.Inject;

public class AccountFragmentViewModel extends ViewModel {

    private final LiveData<Resource<UserLocalObject>> userLocalObjectLiveData;
    @Inject
    public AccountFragmentViewModel(AuthenticationRepository authenticationRepository){
        userLocalObjectLiveData = authenticationRepository.getLocalUserObject();
    }

    public LiveData<Resource<UserLocalObject>> getLocalUserObject() {
        return userLocalObjectLiveData;
    }
}
