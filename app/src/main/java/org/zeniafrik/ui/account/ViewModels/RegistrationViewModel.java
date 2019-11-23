package org.zeniafrik.ui.account.ViewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import org.zeniafrik.api.AuthenticationResponse;
import org.zeniafrik.helper.Resource;
import org.zeniafrik.models.RegisterFormObject;
import org.zeniafrik.repository.AuthenticationRepository;

import javax.inject.Inject;

import static android.arch.lifecycle.Transformations.switchMap;

public class RegistrationViewModel extends ViewModel {

    private final MutableLiveData<RegisterFormObject> registerFormObject = new MutableLiveData<>();
    private final LiveData<Resource<AuthenticationResponse>> response;

    @Inject
    RegistrationViewModel(AuthenticationRepository repository) {
        response = switchMap(registerFormObject, repository::attemptRegister);
    }

    public LiveData<Resource<AuthenticationResponse>> getResponse() {
        return response;
    }

    public void setRegisterFormObject(RegisterFormObject object) {
        registerFormObject.setValue(object);
    }
}
