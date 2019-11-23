package org.zeniafrik.ui.account.ViewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import org.zeniafrik.api.AuthenticationResponse;
import org.zeniafrik.helper.Resource;
import org.zeniafrik.models.Credential;
import org.zeniafrik.repository.AuthenticationRepository;

import javax.inject.Inject;

import static android.arch.lifecycle.Transformations.switchMap;

public class LoginViewModel extends ViewModel {


    private final MutableLiveData<Credential> credential = new MutableLiveData<>();
    private final LiveData<Resource<AuthenticationResponse>> response;


    @Inject
    LoginViewModel(AuthenticationRepository repository) {
        response = switchMap(credential, repository::attemptLogin);
    }


    public LiveData<Resource<AuthenticationResponse>> getResponse() {
        return response;
    }

    public void attemptLogin(Credential cr) {
        this.credential.setValue(cr);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
//        Log.i(LoginViewModel.class.getSimpleName(),"Cleared");
    }

}
